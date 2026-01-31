.intel_syntax noprefix
.global _start

.section .data
addr:
.2byte 2 # AF_INET
.2byte 0x5000 # порт 80
.4byte 0 # нолики для выравнивания
.8byte 0

message: # Респонс сервера, который откидываем
.ascii "HTTP/1.0 200 OK\r\n\r\n"
len = . - message # Его длинна

request: # Воспринимаемый реквест от клиента
.space 512 # Выделяем 512 байт

to_write: # От клиента парсим записываем, закидываем сюда
.space 256

path: # Путь до файлика
.space 17

readed: # Читаемое из файлика
.space 256

.section .bss
sfd: .quad 0 # socket file discriptor
afd: .quad 0 # accept fd
ffd: .quad 0 # file fd
lrd: .quad 0 # len read
lwr: .quad 0 # len write
lrrd: .quad 0 # len request read


.section .text
_start:
mov rax, 41 # socket
mov rdi, 2 # AF_INET
mov rsi, 1 # SOCK_STREAM
mov rdx, 0 # IPPROTO_IP
syscall

mov [rip + sfd], rax # socket fd

cmp rax, 0 #  0 или 0 > это норм, иначе ошибка, нужно с кдом 1 выходить
jl error

mov rax, 49 # bind, привязываем к некому ip
mov rdi, [rip + sfd] # socket fd
lea rsi, [rip + addr] # ссылка на структуру адреса
mov rdx, 16 # её длина
syscall

cmp rax, 0
jl error

mov rax, 50 # listen
mov rdi, [rip + sfd] # socket fd
mov rsi, 0 # LISTEN_BACKLOG
syscall

cmp rax, 0
jl error

accepting_loop:
mov rax, 43 # accept
mov rdi, [rip + sfd] 
xor rsi, rsi # NULL
xor rdx, rdx # NULL
syscall

mov [rip + afd], rax

cmp rax, 0
jl error

mov rax, 57 # fork, создаем дочерний процесс, чтобы можно было обрабатывать
syscall

cmp rax, 0 # Если у нас больше 0, то переходим в действия дочернего процесса, если нет, то у нас родительский процесс
je child
jl error

parrent:
mov rax, 3 # close
mov rdi, [rip + afd] # закрываем accept
syscall

cmp rax, 0
jl error
jmp accepting_loop

child:
mov rax, 3 #close
mov rdi, [rip + sfd] # socket fd
syscall

cmp rax, 0
jl error

mov rax, 0 # read
mov rdi, [rip + afd] # accept fd
lea rsi, [rip + request] # в request записываем запрос
mov rdx, 512 # buff
syscall

mov [rip + lrrd], rax # записываем прочитанное

mov al, byte ptr [rip + request] # берём адрес, куда записали
cmp al, 0x47 # Проверка на G, чтобы определить GET или POST запрос пришёл
je GET

cmp rax, 0
jl error

POST:
lea rsi, [rip + request] # берём адрес от начала запроса
parse_start:
mov rcx, 0

mov al, byte ptr [rsi + rcx] # берём байт этот
cmp al, 0x0d # сравниваем с \r
jne next # если не тот, то переходим на следующию итерацию

inc rcx # потом следующий смотрим
mov al, byte ptr [rsi + rcx] 
cmp al, 0x0a # \n
jne next

inc rcx
mov al, byte ptr [rsi + rcx]
cmp al, 0x0d
jne next

inc rcx
mov al, byte ptr [rsi + rcx]
cmp al, 0x0a
jne next

add rsi, rcx # тк у нас rcx отсчитывается, но сам rsi не изменяется его необходимо прибавить
inc rsi
mov rbx, rsi # нынешнее положение в памяти
lea rdx, [rip + request] # адрес начала запроса
sub rbx, rdx # Получаем Сколько у нас заголовки запроса
mov rdx, [rip + lrrd] # изначальная длинна запроса
sub rdx, rbx # Считаем длину к записи
mov [rip + lwr], rdx

lea rdi, [rip + to_write] # берём адрес, куда писать
mov rbx, [rip + lwr] # сколько будем записывать

moving_content:

cmp rbx, 0 # Проверка конец ли записи
je continue # Переход к дальнейшему исполнению программы

mov al, byte ptr [rsi] # побайтово переносим контент в память, выделенную для записи
mov byte ptr [rdi], al
inc rsi # переходим к след байту
inc rdi
dec rbx # уменьшение счётчика записи
jmp moving_content # на след итерацию

next:
inc rsi # увеличиваем адрес чтения
jmp parse_start # на след итерацию

continue:
lea rsi, [rip + request] # адрес реквеста
lea rdi, [rip + path] # адрес получаемого пути из запроса
lea rsi, [rsi + 5] # Первые 5 символов - "POST "
movsq # Переносим 8 байт
movsq

mov rax, 2 # open
lea rdi, [rip + path] # по полученному пути
mov rsi, 65 # флаги O_WRONLY|O_CREAT
mov rdx, 0777 # права
syscall

mov [rip + ffd], rax # file fd

cmp rax, 0
jl error

mov rax, 1 # Пишем
mov rdi, [rip + ffd] # По file fd
lea rsi, [rip + to_write] # Пишем, полученную из запроса строку
mov rdx, [rip + lwr] # Сколько пишем
syscall

mov [rip + lrd], rax

cmp rax, 0
jl error

mov rax, 3 # close
mov rdi, [rip + ffd] # закрываем file fd
syscall

cmp rax, 0
jl error

mov rax, 1 # write
mov rdi, [rip + afd] # accept fd
lea rsi, [rip + message] # откидываем ответ HTTP
mov rdx, 19
syscall

cmp rax, 0
jl error

jmp END # Переход к концу fork

GET: # Если получаем GET
lea rsi, [rip + request] # адрес реквеста
lea rdi, [rip + path] # адрес получаемого пути из запроса
lea rsi, [rsi + 4] # Первые 4 символа - "GET "
movsq
movsq

mov rax, 2 # open
lea rdi, [rip + path] # по полученному пути
mov rsi, 00 # O_RDONLY
mov rdx, 00400 # Права
syscall

mov [rip + ffd], rax

cmp rax, 0
jl error

mov rax, 0 # read
mov rdi, [rip + ffd] # из файла
lea rsi, [rip + readed] # записываем, что прочитано из файла
mov rdx, 256
syscall

mov [rip + lrd], rax # сколько прочитали

cmp rax, 0
jl error

mov rax, 3 # close
mov rdi, [rip + ffd]
syscall

cmp rax, 0
jl error

mov rax, 1 # write
mov rdi, [rip + afd] # accept fd
lea rsi, [rip + message] # HTTP ответ
mov rdx, 19
syscall

cmp rax, 0
jl error

mov rax, 1 # write
lea rsi, [rip + readed] # адрес Прочитанного
mov rdi, [rip + afd] # Откидываем клиенту прочитанное
mov rdx, [rip + lrd]
syscall

cmp rax, 0
jl error

END:
mov rax, 60 # exit из fork
mov rdi, 0 # код ошибки
xor rdx, rdx # зануляем
xor rsi, rsi
syscall

cmp rax, 0
jl error

mov rax, 3 # close
mov rdi, [rip + afd] # accept fd
syscall

cmp rax, 0
jl error

jmp accepting_loop # возвращаемся, чтобы обслужить следующий запрос

xor rdx, rdx
xor rsi, rsi
mov rax, 60 # exit уже из приложения
mov rdi, 0
syscall

error: # если ошибка, то выходим с кодом 1
xor rdx, rdx
xor rsi, rsi
mov rax, 60
mov rdi, 1
syscall