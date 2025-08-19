package com.lumen.minuAPI.OpenAPI.Brainfuck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class bfController {
    
    @GetMapping("/brainfuck/{code}")
    public String brainfuck(
        @PathVariable() String code,
        @RequestParam(required = false) String input
    ) {
        char[] tape = new char[16]; // 테이프16칸
        int t_ptr = 0, c_ptr = 0; // 테이프 포인터, 코드 포인터
        int maxrun = 1000, currun = 0; // 최대 실행 횟수, 현재 실행 횟수
        int inputIndex = 0; // 입력 인덱스
        StringBuilder output = new StringBuilder(); // 결과

        code = code.replaceAll("[^\\(\\)<>+\\-.,]", ""); // 필터링 추출
        if (code.isEmpty()) { return "Error: No input valid code!"; }

        while (currun < maxrun && c_ptr < code.length()) {
            char command = code.charAt(c_ptr);
            switch (command) {
                case '>':
                    t_ptr++;
                    if (t_ptr >= tape.length) { return "Error: Tape overflow!, Tape MaxSize is 16"; }
                    break;
                case '<':
                    t_ptr--;
                    if (t_ptr < 0) { return "Error: Tape underflow!, Tape MinSize is 0"; }
                    break;
                case '+':
                    tape[t_ptr]++;
                    break;
                case '-':
                    tape[t_ptr]--;
                    break;
                case '.':
                    output.append(tape[t_ptr]);
                    break;
                case ',':
                    if (
                        input != null && inputIndex < input.length()
                    ) {
                        tape[t_ptr] = input.charAt(inputIndex++);
                    }
                    else { return "Error: Not enough input values!"; }
                    break;
                case '(':
                    if (tape[t_ptr] == 0) {
                        int loop = 1;
                        while (loop > 0 && ++c_ptr < code.length()) {
                            if (code.charAt(c_ptr) == '(') { loop++; }
                            else if (code.charAt(c_ptr) == ')') { loop--; }
                        }
                        if (loop != 0) { return "Error: Unmatched pair '('!"; }
                    }
                    break;
                case ')':
                    if (tape[t_ptr] != 0) {
                        int loop = 1;
                        while (loop > 0 && --c_ptr >= 0) {
                            if (code.charAt(c_ptr) == '(') { loop--; }
                            else if (code.charAt(c_ptr) == ')') { loop++; }
                        }
                        if (loop != 0) { return "Error: Unmatched pair ')'!"; }
                    }
                    break;
                default:
                    break;
            }
            c_ptr++; currun++;
        }

        if (currun >= maxrun) { return "Error: Maximum execution limit exceeded!"; }
        else if (c_ptr >= code.length()) { return output.toString(); }
        else { return "Error: Execution interrupted unexpectedly!"; }
    }
}
