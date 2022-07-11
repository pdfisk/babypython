/**
 * MIT License
 *
 * Copyright (c) 2022 Peter Fisk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.babypython.client.python.parser;

import net.babypython.client.python.antlr.Python3Lexer;
import net.babypython.client.python.antlr.Python3Parser;
import net.babypython.client.vm.containers.lists.PythonTokenList;
import net.babypython.client.vm.util.streams.StringStream;
import org.antlr.v4.runtime.*;

import java.util.List;

public class Parser extends Python3Parser {

    public static File_inputContext parseSrc(String src) {
        return createParser(src).file_input();
    }

    public static String parseToString(String src) {
        Parser parser = createParser(src);
        File_inputContext ctx = parseSrc(src);
        return ctx.toStringTree(parser);
    }

    public static PythonTokenList parseTokens(String src) {
        Python3Lexer lexer = createLexer(src);
        List<? extends Token> allTokens = lexer.getAllTokens();
        PythonTokenList tokenList = new PythonTokenList();
        for (Token token : allTokens)
            tokenList.add(token);
        return tokenList;
    }

    public static String parseTokensToString(String src) {
        Python3Lexer lexer = createLexer(src);
        List<? extends Token> allTokens = lexer.getAllTokens();
        StringStream stringStream = new StringStream();
        for (Token token : allTokens)
            stringStream.prn(token.toString());
        return stringStream.toString();
    }

    static Python3Lexer createLexer(String src) {
        CharStream charStream = CharStreams.fromString(src);
        return new Python3Lexer(charStream);
    }

    static Parser createParser(String src) {
        return new Parser(createTokenStream(src));
    }

    static CommonTokenStream createTokenStream(String src) {
        return new CommonTokenStream(createLexer(src));
    }

    public Parser(TokenStream input) {
        super(input);
    }
}
