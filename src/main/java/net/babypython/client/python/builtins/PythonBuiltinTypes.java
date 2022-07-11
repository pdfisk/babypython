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
package net.babypython.client.python.builtins;

import net.babypython.client.python.builtins.types.sprite.sprites.*;
import net.babypython.client.python.core.PythonObject;
import net.babypython.client.python.core.abstract_.PythonType;
import net.babypython.client.vm.containers.dictionaries.BuiltinTypeDictionary;
import net.babypython.client.vm.containers.dictionaries.LocalDictionary;
import net.babypython.client.vm.vm.interfaces.IStdOut;

public class PythonBuiltinTypes {

    public static PythonObject instantiate(IStdOut stdOut, String typeName, LocalDictionary localDictionary, Object[] args) {
        return getInstance().instantiateType(stdOut, typeName, localDictionary, args);
    }

    PythonBuiltinTypes() {
        typeDictionary = new BuiltinTypeDictionary();
        addBuiltinTypes();
    }

    void addBuiltinType(PythonType pythonBuiltinType) {
        typeDictionary.put(pythonBuiltinType.getName(), pythonBuiltinType);
    }

    void addBuiltinTypes() {
        addBuiltinType(new SpriteCatLeftType());
        addBuiltinType(new SpriteCatRightType());
        addBuiltinType(new SpriteChickLeftType());
        addBuiltinType(new SpriteChickRightType());
        addBuiltinType(new SpriteDogAtHomeType());
        addBuiltinType(new SpriteDogHouseType());
        addBuiltinType(new SpriteDuckFrontType());
        addBuiltinType(new SpriteDuckLeftType());
        addBuiltinType(new SpriteDucklingInDogHouseType());
        addBuiltinType(new SpriteDuckRightType());
        addBuiltinType(new SpriteFenceType());
        addBuiltinType(new SpriteHenWithChickType());
        addBuiltinType(new SpriteLightHenLeftType());
        addBuiltinType(new SpriteLightHenRightType());
        addBuiltinType(new SpriteOrangeDucklingLeftType());
        addBuiltinType(new SpriteOrangeDucklingRightType());
        addBuiltinType(new SpritePumpType());
        addBuiltinType(new SpritePumpWithDogAndDucklingType());
        addBuiltinType(new SpritePumpWithDogType());
    }

    public PythonObject instantiateType(IStdOut stdOut, String typeName, LocalDictionary localDictionary, Object[] args) {
        if (!typeDictionary.containsKey(typeName))
            return null;
        PythonType pythonBuiltinType = typeDictionary.get(typeName);
        return pythonBuiltinType.instantiate(stdOut, localDictionary, args);
    }

    public static PythonBuiltinTypes getInstance() {
        if (instance == null)
            instance = new PythonBuiltinTypes();
        return instance;
    }

    BuiltinTypeDictionary typeDictionary;
    static PythonBuiltinTypes instance;
}
