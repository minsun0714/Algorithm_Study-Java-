# 구현

def solution(s):
    answer = 0
    for i in range(len(s)):
        for j in range(len(s) - 1, i - 1, -1):
            if s[i:j + 1] == s[i:j + 1][::-1]:
                answer = max(answer, j - i + 1)

    return answer

