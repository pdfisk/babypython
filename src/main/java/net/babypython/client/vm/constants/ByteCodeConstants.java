/**
 * MIT License
 * <p>
 * Copyright (c) 2022 Peter Fisk
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.babypython.client.vm.constants;

public class ByteCodeConstants {
    public static final int OpCompEq = 1009;
    public static final int OpCompGe = 1010;
    public static final int OpCompGt = 1011;
    public static final int OpCompLe = 1012;
    public static final int OpCompLt = 1013;
    public static final int OpCompNeq = 1014;
    public static final int OpDefFunction = 1015;
    public static final int OpFork = 1017;
    public static final int OpForkWithArgs = 1018;
    public static final int OpJumpIfFalse = 1019;
    public static final int OpLineChanged = 1021;
    public static final int OpPushArray = 1024;
    public static final int OpPushAssociation = 1025;
    public static final int OpPushDictionary = 1027;
    public static final int OpPushFalse = 1028;
    public static final int OpPushNone = 1032;
    public static final int OpPushProperty = 1033;
    public static final int OpPushSelf = 1034;
    public static final int OpPushTrue = 1035;
    public static final int OpPythonCallFunction = 1036;
    public static final int OpPythonCallNamedFunction = 1037;
    public static final int OpPythonInstantiateType = 1038;
    public static final int OpSendMessage = 1040;
    public static final int OpSendMessageWithArgs = 1041;
    public static final int OpSendSuperMessage = 1042;
    public static final int OpSendSuperMessageWithArgs = 1043;
    public static final int OpTraceMessage = 1044;
    public static final int OpTraceVariable = 1045;
    public static final int OpValue = 1046;
    public static final int OpValueWithArgs = 1047;

    public static final int POP_TOP = 1;
    public static final int DUP_TOP = 4;
    public static final int BINARY_MULTIPLY = 20;
    public static final int BINARY_MODULO = 22;
    public static final int BINARY_ADD = 23;
    public static final int BINARY_SUBTRACT = 24;
    public static final int BINARY_TRUE_DIVIDE = 27;
    public static final int RETURN_VALUE = 83;
    public static final int STORE_NAME = 90;
    public static final int STORE_ATTR = 95;
    public static final int STORE_GLOBAL = 97;
    public static final int LOAD_CONST = 100;
    public static final int LOAD_NAME = 101;
    public static final int LOAD_ATTR = 106;
    public static final int JUMP_ABSOLUTE = 113;
    public static final int LOAD_GLOBAL = 116;
}

//*POP_TOP                        1
// ROT_TWO                        2
// ROT_THREE                      3
//*DUP_TOP                        4
// DUP_TOP_TWO                    5
// ROT_FOUR                       6
// NOP                            9
// UNARY_POSITIVE                10
// UNARY_NEGATIVE                11
// UNARY_NOT                     12
// UNARY_INVERT                  15
// BINARY_MATRIX_MULTIPLY        16
// INPLACE_MATRIX_MULTIPLY       17
// BINARY_POWER                  19
//*BINARY_MULTIPLY               20
//*BINARY_MODULO                 22
//*BINARY_ADD                    23
//*BINARY_SUBTRACT               24
// BINARY_SUBSCR                 25
// BINARY_FLOOR_DIVIDE           26
//*BINARY_TRUE_DIVIDE            27
// INPLACE_FLOOR_DIVIDE          28
// INPLACE_TRUE_DIVIDE           29
// GET_AITER                     50
// GET_ANEXT                     51
// BEFORE_ASYNC_WITH             52
// BEGIN_FINALLY                 53
// END_ASYNC_FOR                 54
// INPLACE_ADD                   55
// INPLACE_SUBTRACT              56
// INPLACE_MULTIPLY              57
// INPLACE_MODULO                59
// STORE_SUBSCR                  60
// DELETE_SUBSCR                 61
// BINARY_LSHIFT                 62
// BINARY_RSHIFT                 63
// BINARY_AND                    64
// BINARY_XOR                    65
// BINARY_OR                     66
// INPLACE_POWER                 67
// GET_ITER                      68
// GET_YIELD_FROM_ITER           69
// PRINT_EXPR                    70
// LOAD_BUILD_CLASS              71
// YIELD_FROM                    72
// GET_AWAITABLE                 73
// INPLACE_LSHIFT                75
// INPLACE_RSHIFT                76
// INPLACE_AND                   77
// INPLACE_XOR                   78
// INPLACE_OR                    79
// WITH_CLEANUP_START            81
// WITH_CLEANUP_FINISH           82
//*RETURN_VALUE                  83
// IMPORT_STAR                   84
// SETUP_ANNOTATIONS             85
// YIELD_VALUE                   86
// POP_BLOCK                     87
// END_FINALLY                   88
// POP_EXCEPT                    89
// HAVE_ARGUMENT                 90
//*STORE_NAME                    90
// DELETE_NAME                   91
// UNPACK_SEQUENCE               92
// FOR_ITER                      93
// UNPACK_EX                     94
// STORE_ATTR                    95
// DELETE_ATTR                   96
//*STORE_GLOBAL                  97
// DELETE_GLOBAL                 98
//*LOAD_CONST                   100
//*LOAD_NAME                    101
// BUILD_TUPLE                  102
// BUILD_LIST                   103
// BUILD_SET                    104
// BUILD_MAP                    105
// LOAD_ATTR                    106
// COMPARE_OP                   107
// IMPORT_NAME                  108
// IMPORT_FROM                  109
// JUMP_FORWARD                 110
// JUMP_IF_FALSE_OR_POP         111
// JUMP_IF_TRUE_OR_POP          112
//*JUMP_ABSOLUTE                113
// POP_JUMP_IF_FALSE            114
// POP_JUMP_IF_TRUE             115
//*LOAD_GLOBAL                  116
// SETUP_FINALLY                122
// LOAD_FAST                    124
// STORE_FAST                   125
// DELETE_FAST                  126
// RAISE_VARARGS                130
// CALL_FUNCTION                131
// MAKE_FUNCTION                132
// BUILD_SLICE                  133
// LOAD_CLOSURE                 135
// LOAD_DEREF                   136
// STORE_DEREF                  137
// DELETE_DEREF                 138
// CALL_FUNCTION_KW             141
// CALL_FUNCTION_EX             142
// SETUP_WITH                   143
// EXTENDED_ARG                 144
// LIST_APPEND                  145
// SET_ADD                      146
// MAP_ADD                      147
// LOAD_CLASSDEREF              148
// BUILD_LIST_UNPACK            149
// BUILD_MAP_UNPACK             150
// BUILD_MAP_UNPACK_WITH_CALL   151
// BUILD_TUPLE_UNPACK           152
// BUILD_SET_UNPACK             153
// SETUP_ASYNC_WITH             154
// FORMAT_VALUE                 155
// BUILD_CONST_KEY_MAP          156
// BUILD_STRING                 157
// BUILD_TUPLE_UNPACK_WITH_CALL 158
// LOAD_METHOD                  160
// CALL_METHOD                  161
// CALL_FINALLY                 162
// POP_FINALLY                  163
