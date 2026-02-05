~~~ C
__int64 __fastcall main(int a1, char **a2, char **a3)
{
  __m128i *v4; // rax
  void *v5; // rax
  const __m128i *v6; // rax
  __m128i *addr; // [rsp+0h] [rbp-38h]
  void *v8; // [rsp+8h] [rbp-30h]

  if ( a1 != 2
    || strlen(a2[1]) != 32
    || (v4 = (__m128i *)mmap(0, (size_t)&unk_3C000, 7, 34, -1, 0), v4 == (__m128i *)-1LL) )
  {
    puts("no");
    return 1;
  }
  addr = v4;
  v5 = mmap(0, (size_t)&unk_3C000, 7, 34, -1, 0);
  if ( v5 == (void *)-1LL )
  {
    puts("no");
    munmap(addr, (size_t)&unk_3C000);
    return 1;
  }
  v8 = v5;
  v6 = (const __m128i *)a2[1];
  *addr = _mm_loadu_si128(v6);
  addr[1] = _mm_loadu_si128(v6 + 1);
  memcpy(&addr[2], &unk_4060, (size_t)&unk_3BB86);
  if ( (unsigned __int8)sub_12C0(addr, v8) )
  {
    puts("yes");
    return 0;
  }
  else
  {
    puts("no");
    return 2;
  }
}
~~~