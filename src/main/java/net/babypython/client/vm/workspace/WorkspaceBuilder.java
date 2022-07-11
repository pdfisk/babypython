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
package net.babypython.client.vm.workspace;

import net.babypython.client.vm.classes.special.classes.*;
import net.babypython.client.vm.constants.PythonTypeConstants;
import net.babypython.client.vm.constants.PythonVarNameConstants;
import net.babypython.client.vm.classes.special.objects.NilObject;
import net.babypython.client.vm.core.VClass;
import net.babypython.client.vm.core.VObject;
import net.babypython.client.ui.util.Logable;

import java.util.HashMap;

public class WorkspaceBuilder extends Logable {

    public static VClass getVClass(String className) {
        return getInstance().getBuiltinClass(className);
    }

    public static void initializeWorkspace() {
        getInstance().buildAll();
    }

    void buildAll() {
        initSpecialClasses();
        initClasses();
//        Timer t = new Timer() {
//            @Override
//            public void run() {
//                initObjects();
//                Workspace.getInstance().loadImage();
//            }
//        };
//        t.schedule(100);
    }

    VClass createClass(String className) {
        VClass vClass;
        if (specialClassesMap.containsKey(className))
            vClass = specialClassesMap.get(className);
        else
            vClass = new VClass(className);
        vClass.setIsBuiltin();
        classesMap.put(className, vClass);
        objectSpace.putGlobal(className, vClass);
        return vClass;
    }

    VClass getBuiltinClass(String className) {
        if (!classesMap.containsKey(className))
            return null;
        return classesMap.get(className);
    }

    void addSpecialClass(VClass vClass) {
        specialClassesMap.put(vClass.getClassName(), vClass);
    }

    void initSpecialClasses() {
        specialClassesMap.clear();
        addSpecialClass(ArrayClass.getInstance());
        addSpecialClass(BehaviorClass.getInstance());
        addSpecialClass(BlockContextClass.getInstance());
        addSpecialClass(BoardPanelClass.getInstance());
        addSpecialClass(BooleanClass.getInstance());
        addSpecialClass(DictionaryClass.getInstance());
        addSpecialClass(FalseClass.getInstance());
        addSpecialClass(FloatClass.getInstance());
        addSpecialClass(ImagePanelClass.getInstance());
        addSpecialClass(IntegerClass.getInstance());
        addSpecialClass(ListViewClass.getInstance());
        addSpecialClass(OrderedCollectionClass.getInstance());
        addSpecialClass(StringClass.getInstance());
        addSpecialClass(TextPanelClass.getInstance());
        addSpecialClass(TranscriptWindowClass.getInstance());
        addSpecialClass(TrueClass.getInstance());
        addSpecialClass(UndefinedClass.getInstance());
        addSpecialClass(UnknownClass.getInstance());
        addSpecialClass(ViewClass.getInstance());
        addSpecialClass(WidgetClass.getInstance());
        addSpecialClass(WindowClass.getInstance());
        addSpecialClass(WindowButtonClass.getInstance());
    }

    void initClasses() {
        classesMap.clear();
        VClass arrayClass = createClass(PythonTypeConstants.ClassArray);
        VClass arrayedCollectionClass = createClass(PythonTypeConstants.ClassArrayedCollection);
        VClass behaviorClass = createClass(PythonTypeConstants.ClassBehavior);
        VClass blockContextClass = createClass(PythonTypeConstants.ClassBlockContext);
        VClass boardPanelClass = createClass(PythonTypeConstants.ClassBoardPanel);
        VClass booleanClass = createClass(PythonTypeConstants.ClassBoolean);
        VClass characterClass = createClass(PythonTypeConstants.ClassCharacter);
        VClass classClass = createClass(PythonTypeConstants.ClassClass);
        VClass collectionClass = createClass(PythonTypeConstants.ClassCollection);
        VClass compiledMethodClass = createClass(PythonTypeConstants.ClassCompiledMethod);
        VClass compilerClass = createClass(PythonTypeConstants.ClassCompiler);
        VClass consoleWindowClass = createClass(PythonTypeConstants.ClassConsoleWindow);
        VClass contextClass = createClass(PythonTypeConstants.ClassContext);
        VClass dateAndTimeClass = createClass(PythonTypeConstants.ClassDateAndTime);
        VClass dictionaryClass = createClass(PythonTypeConstants.ClassDictionary);
        VClass dictionaryInspectorClass = createClass(PythonTypeConstants.ClassDictionaryInspector);
        VClass durationClass = createClass(PythonTypeConstants.ClassDuration);
        VClass falseClass = createClass(PythonTypeConstants.ClassFalse);
        VClass floatClass = createClass(PythonTypeConstants.ClassFloat);
        VClass homeContextClass = createClass(PythonTypeConstants.ClassHomeContext);
        VClass hashedCollectionClass = createClass(PythonTypeConstants.ClassHashedCollection);
        VClass horizontalSplitViewClass = createClass(PythonTypeConstants.ClassHorizontalSplitView);
        VClass identityDictionaryClass = createClass(PythonTypeConstants.ClassIdentityDictionary);
        VClass imagePanelClass = createClass(PythonTypeConstants.ClassImagePanel);
        VClass inspectorClass = createClass(PythonTypeConstants.ClassInspector);
        VClass integerClass = createClass(PythonTypeConstants.ClassInteger);
        VClass listInspectorClass = createClass(PythonTypeConstants.ClassListInspector);
        VClass listViewClass = createClass(PythonTypeConstants.ClassListView);
        VClass magnitudeClass = createClass(PythonTypeConstants.ClassMagnitude);
        VClass metaClassClass = createClass(PythonTypeConstants.ClassMetaClass);
        VClass methodDictionaryClass = createClass(PythonTypeConstants.ClassMethodDictionary);
        VClass numberClass = createClass(PythonTypeConstants.ClassNumber);
        VClass objectClass = createClass(PythonTypeConstants.ClassObject);
        VClass objectInspectorClass = createClass(PythonTypeConstants.ClassObjectInspector);
        VClass orderedCollectionClass = createClass(PythonTypeConstants.ClassOrderedCollection);
        VClass parserClass = createClass(PythonTypeConstants.ClassParser);
        VClass priorityQueueClass = createClass(PythonTypeConstants.ClassPriorityQueue);
        VClass processClass = createClass(PythonTypeConstants.ClassProcess);
        VClass processBrowserClass = createClass(PythonTypeConstants.ClassProcessBrowser);
        VClass processSchedulerClass = createClass(PythonTypeConstants.ClassProcessScheduler);
        VClass queueClass = createClass(PythonTypeConstants.ClassQueue);
        VClass sequenceableCollectionClass = createClass(PythonTypeConstants.ClassSequenceableCollection);
        VClass setClass = createClass(PythonTypeConstants.ClassSet);
        VClass sharedPoolClass = createClass(PythonTypeConstants.ClassSharedPool);
        VClass smalltalkCompilerClass = createClass(PythonTypeConstants.ClassSmalltalkCompiler);
        VClass smalltalkConsoleClass = createClass(PythonTypeConstants.ClassSmalltalkConsole);
        VClass smalltalkEditorClass = createClass(PythonTypeConstants.ClassSmalltalkEditor);
        VClass smalltalkParserClass = createClass(PythonTypeConstants.ClassSmalltalkParser);
        VClass smalltalkTokenizerClass = createClass(PythonTypeConstants.ClassSmalltalkTokenizer);
        VClass sortedCollectionClass = createClass(PythonTypeConstants.ClassSortedCollection);
        VClass splitViewClass = createClass(PythonTypeConstants.ClassSplitView);
        VClass stringClass = createClass(PythonTypeConstants.ClassString);
        VClass stackClass = createClass(PythonTypeConstants.ClassStack);
        VClass symbolClass = createClass(PythonTypeConstants.ClassSymbol);
        VClass systemDictionaryClass = createClass(PythonTypeConstants.ClassSystemDictionary);
        VClass textPanelClass = createClass(PythonTypeConstants.ClassTextPanel);
        VClass tokenizerClass = createClass(PythonTypeConstants.ClassTokenizer);
        VClass transcriptWindowClass = createClass(PythonTypeConstants.ClassTranscriptWindow);
        VClass treeViewClass = createClass(PythonTypeConstants.ClassTreeView);
        VClass trueClass = createClass(PythonTypeConstants.ClassTrue);
        VClass undefinedObjectClass = createClass(PythonTypeConstants.ClassUndefinedObject);
        VClass verticalSplitViewClass = createClass(PythonTypeConstants.ClassVerticalSplitView);
        VClass viewClass = createClass(PythonTypeConstants.ClassView);
        VClass widgetsClass = createClass(PythonTypeConstants.ClassWidget);
        VClass windowClass = createClass(PythonTypeConstants.ClassWindow);
        VClass windowButtonClass = createClass(PythonTypeConstants.ClassWindowButton);
        // behavior
        objectClass.addSubclass(behaviorClass);
        behaviorClass.addSubclass(classClass);
        behaviorClass.addSubclass(metaClassClass);
        // boolean
        objectClass.addSubclass(booleanClass);
        booleanClass.addSubclass(falseClass);
        booleanClass.addSubclass(trueClass);
        // collection
        objectClass.addSubclass(collectionClass);
        collectionClass.addSubclass(dictionaryClass);
        dictionaryClass.addSubclass(identityDictionaryClass);
        identityDictionaryClass.addSubclass(systemDictionaryClass);
        dictionaryClass.addSubclass(methodDictionaryClass);
        hashedCollectionClass.addSubclass(setClass);
        collectionClass.addSubclass(sequenceableCollectionClass);
        sequenceableCollectionClass.addSubclass(arrayedCollectionClass);
        arrayedCollectionClass.addSubclass(arrayClass);
        arrayedCollectionClass.addSubclass(stringClass);
        stringClass.addSubclass(symbolClass);
        sequenceableCollectionClass.addSubclass(orderedCollectionClass);
        orderedCollectionClass.addSubclass(queueClass);
        queueClass.addSubclass(priorityQueueClass);
        orderedCollectionClass.addSubclass(sortedCollectionClass);
        orderedCollectionClass.addSubclass(stackClass);
        // compiler
        objectClass.addSubclass(compilerClass);
        compilerClass.addSubclass(compiledMethodClass);
        compilerClass.addSubclass(smalltalkCompilerClass);
        // context
        objectClass.addSubclass(contextClass);
        contextClass.addSubclass(blockContextClass);
        contextClass.addSubclass(homeContextClass);
        // magnitude
        objectClass.addSubclass(magnitudeClass);
        magnitudeClass.addSubclass(characterClass);
        magnitudeClass.addSubclass(dateAndTimeClass);
        magnitudeClass.addSubclass(durationClass);
        magnitudeClass.addSubclass(numberClass);
        numberClass.addSubclass(floatClass);
        numberClass.addSubclass(integerClass);
        // parser
        objectClass.addSubclass(parserClass);
        parserClass.addSubclass(smalltalkParserClass);
        // process
        objectClass.addSubclass(processClass);
        processClass.addSubclass(processSchedulerClass);
        // shared pool
        objectClass.addSubclass(sharedPoolClass);
        // tokenizer
        objectClass.addSubclass(tokenizerClass);
        tokenizerClass.addSubclass(smalltalkTokenizerClass);
        // undefinedObject
        objectClass.addSubclass(undefinedObjectClass);
        // view
        objectClass.addSubclass(viewClass);
        viewClass.addSubclass(widgetsClass);
        widgetsClass.addSubclass(boardPanelClass);
        widgetsClass.addSubclass(imagePanelClass);
        widgetsClass.addSubclass(listViewClass);
        widgetsClass.addSubclass(splitViewClass);
        splitViewClass.addSubclass(horizontalSplitViewClass);
        splitViewClass.addSubclass(verticalSplitViewClass);
        widgetsClass.addSubclass(textPanelClass);
        textPanelClass.addSubclass(smalltalkEditorClass);
        widgetsClass.addSubclass(treeViewClass);
        viewClass.addSubclass(windowClass);
        windowClass.addSubclass(consoleWindowClass);
        consoleWindowClass.addSubclass(smalltalkConsoleClass);
        windowClass.addSubclass(inspectorClass);
        inspectorClass.addSubclass(dictionaryInspectorClass);
        inspectorClass.addSubclass(listInspectorClass);
        inspectorClass.addSubclass(objectInspectorClass);
        windowClass.addSubclass(processBrowserClass);
        windowClass.addSubclass(transcriptWindowClass);
        widgetsClass.addSubclass(windowButtonClass);
    }

    void initObjects() {
        // nil
        VObject nilObject = NilObject.getInstance();
        nilObject.setVClass(getVClass(PythonTypeConstants.ClassUndefinedObject));
        objectSpace.putGlobal(PythonVarNameConstants.GlobalNameNone, nilObject);
        objectSpace.getObjectClass().setSuperClass(nilObject);
    }

    public static WorkspaceBuilder getInstance() {
        if (instance == null)
            instance = new WorkspaceBuilder();
        return instance;
    }

    HashMap<String, VClass> classesMap = new HashMap<>();
    Workspace objectSpace = Workspace.getInstance();
    HashMap<String, VClass> specialClassesMap = new HashMap<>();
    static WorkspaceBuilder instance;
}
