
Были найдены пакеты, нешифрованные, которые посылает сервер, и пакеты, которые посылает клиент.
![[Pasted image 20250915114659.png]]
![[Pasted image 20250915114733.png]]
Был найден участок кода методом тыка, с посыланием сервером пакетов с форматом lol[|999|WuAlSdDr|]kek
где 999 - кол-во звёзд
![[Pasted image 20250915115147.png]]
WuAlSdDr - WASD - кнопки, uldr - направления up, left, down, right

Клиент же посылал пакеты вида  lol[w+a+s+d+++]kek, где wasd - нажатые клавиши
![[Pasted image 20250915115402.png]]

Вот такими темпами было решено поднять прокси сервер, который принимал пакеты серва, редактил их на адекватное WuAlSdDr, чтобы клиент по человечески это дело воспринимал, и редактил пакеты клиента, так, чтобы посылалась та кнопка, которая по мнения сервера отвечает за нужное движение. Например послано WrAdSrDu, клиент движется по человечески вверх, хочет послать w, но это дело ловим, сопоставляем, что чтобы идти вверх, нужно нажать d, заменяем в пакете w на d.
![[Pasted image 20250915120613.png]]
Потом за пару минут ручками проходим. Но лучше много кнопок одновременно не нажимать, тк все будут меняться на одну, пойдёт рассинхрон с сервером

Навайбкожено, конечно, но логику подмены пришлось самому делать
~~~ python
#!/usr/bin/env python3
import socket
import threading
import select
import re
import time
import random

class WinningProxy:
    def __init__(self, listen_port, target_host, target_port):
        self.listen_port = listen_port
        self.target_host = target_host
        self.target_port = target_port
        self.current_stars = 472
        self.stars_modified = False
        self.win_sequence = []
        self.serv_move = "" 
        
    def generate_win_sequence(self):
        """Генерируем выигрышную последовательность движений"""
        sequences = [
            "++++++",
            "d+d+d+", 
            "a+a+a+",  
            "w+w+w+", 
            "s+s+s+",
        ]
        return random.choice(sequences)
    
    def optimize_movements(self, movement_code):
        """Оптимизируем движения для быстрой победы"""
        if movement_code != "WuAlSdDr":
            return "WuAlSdDr"
        return movement_code
    
    def client_move(self, pressed):
        """Подменяем нажатую клавишу на соответствующую кнопку из serv_move.
           Возвращает кнопку (w/a/s/d), на которую нужно заменить pressed."""
        if not self.serv_move or len(self.serv_move) < 8:
            return pressed  # Если нет данных от сервера, ничего не меняем
        
        good = 'WuAlSdDr'
        print(f'Original: {self.serv_move}')
        ans = "d"
        dr = ""
        pressed = pressed.lower()
        print(f'Pressed: {pressed}')
        

        if pressed == self.serv_move[0].lower():
            dr = good[1]
        if pressed == self.serv_move[2].lower():
            dr = good[3]
        if pressed == self.serv_move[4].lower():
            dr = good[5]
        if pressed == self.serv_move[6].lower():
            dr = good[7]
        print(f'DR: {dr}')
        for i in range(8):
            if(dr == self.serv_move[i]):
                ans = self.serv_move[i - 1].lower()
                print(f'Match: {dr}::{self.serv_move[i]}')
        
        print(f'Returned: {ans}')
        return ans
        
    def modify_server_response(self, data):
        """Модифицируем ответы сервера"""
        try:
            text = data.decode('utf-8', errors='ignore')
            
            # Ищем количество звезд
            star_match = re.search(r'lol\{[^}]+\}\|(\d+)\|', text)
            if star_match:
                self.current_stars = int(star_match.group(1))
                print(f"★ Stars: {self.current_stars}")
                
                if not self.stars_modified and self.current_stars <= 472:
                    modified_text = text.replace(
                        f"|{self.current_stars}|", 
                        "|1|"
                    )
                    self.stars_modified = True
                    print(f"★ SET STARS TO 1")
                    return modified_text.encode('utf-8')
            
            # Сохраняем движение сервера
            movement_match = re.search(r'\|([WASDwasdulr]+)\|', text)
            if movement_match:
                self.serv_move = movement_match.group(1)
                print(f"🎮 Server movement pattern: {self.serv_move}")
                
                optimized_code = self.optimize_movements(self.serv_move)
                if optimized_code != self.serv_move:
                    modified_text = text.replace(
                        f"|{self.serv_move}|",
                        f"|{optimized_code}|"
                    )
                    print(f"🎮 Optimized movements: {self.serv_move} → {optimized_code}")
                    return modified_text.encode('utf-8')
                    
        except Exception as e:
            print(f"Modify error: {e}")
            
        return data
        
    def modify_client_commands(self, data):
        """Модифицируем команды клиента.
           Выводим все входящие пакеты и, если возможно, подменяем нажатую клавишу."""
        try:
            text = data.decode('utf-8', errors='ignore')
        except Exception:
            # Невозможно раскодировать — печатаем raw bytes и не меняем
            print(f"📦 Raw client packet (bytes): {data!r}")
            return data

        # Отладка: выводим ВСЕ входящие пакеты клиента
        print(f"📦 Raw client packet: {repr(text)}")

        try:
            # Пытаемся найти формат lol[...]kek
            m = re.search(r'lol\[(.*?)\]kek', text)
            pressed_key = None
            original_fragment = None
            if m:
                original_fragment = m.group(1)
                # Ищем внутри фрагмента первую клавишу w/a/s/d
                for key in ['w', 'a', 's', 'd']:
                    if key in original_fragment:
                        pressed_key = key
                        break
            else:
                # Если нет формата lol[...]kek, просто ищем первую встреченную клавишу в тексте
                for key in ['w', 'a', 's', 'd']:
                    if key in text:
                        pressed_key = key
                        break

            if pressed_key and self.serv_move:
                # Подменяем на правильную кнопку
                converted_key = self.client_move(pressed_key)
                # Формируем замену: если был формат lol[...]kek — заменим внутри него,
                # иначе отправим упрощённый стандарт lol[x]kek
                if m:
                    
                    myp = original_fragment.replace(pressed_key, converted_key)
                    simplified = text.replace(f"lol[{original_fragment}]kek", f"lol[{myp}]kek")
                else:
                    # Сохраняем оригинал для логирования, но на сервер отправим стандартный пакет
                    simplified = f"lol[{converted_key}]kek"

                # Логируем исходную и подменённую команды
                print(f"🎯 Client pressed: {pressed_key} | Sent as: {converted_key}")
                print(f"    {text}  →  {simplified}")

                return simplified.encode('utf-8')
            else:
                # Ничего не меняем, но логируем причину
                if not pressed_key:
                    print("⚪ No movement key found in client packet — leaving unchanged.")
                elif not self.serv_move:
                    print("⚪ No serv_move known yet — cannot convert, leaving unchanged.")
                print(f"    Returned as-is: {text}")
        except Exception as e:
            print(f"Command modify error: {e}")

        return data
    
    def handle_client(self, client_socket):
        try:
            server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            server_socket.settimeout(3)
            server_socket.connect((self.target_host, self.target_port))
            print("✅ Connected to game server")
            
            sockets = [client_socket, server_socket]
            
            while True:
                readable, _, errors = select.select(sockets, [], sockets, 1.0)
                
                if errors:
                    break
                    
                for sock in readable:
                    try:
                        data = sock.recv(4096)
                        if not data:
                            return
                            
                        if sock is client_socket:
                            optimized_data = self.modify_client_commands(data)
                            server_socket.send(optimized_data)
                            
                        else:
                            optimized_data = self.modify_server_response(data)
                            client_socket.send(optimized_data)
                            
                    except socket.error:
                        return
                        
        except Exception as e:
            print(f"❌ Connection error: {e}")
        finally:
            try:
                client_socket.close()
            except Exception:
                pass
            try:
                server_socket.close()
            except Exception:
                pass
            print("Connection closed")
    
    def start(self):
        server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        server.bind(('0.0.0.0', self.listen_port))
        server.listen(5)
        
        print(f"🎮 Winning Proxy started on port {self.listen_port}")
        print("🚀 Will convert client movements to server format")
        
        try:
            while True:
                client_socket, addr = server.accept()
                print(f"👤 New player: {addr}")
                
                thread = threading.Thread(target=self.handle_client, args=(client_socket,))
                thread.daemon = True
                thread.start()
                
        except KeyboardInterrupt:
            print("\n🛑 Shutting down...")
        finally:
            server.close()

if __name__ == "__main__":
    proxy = WinningProxy(30037, "jetski-455wf7wv.alfactf.ru", 30036)
    proxy.start()
~~~