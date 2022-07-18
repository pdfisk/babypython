Blockly.Python['controls_while'] = function (block) {
    var text_1 = Blockly.Python.valueToCode(block, 'cond', Blockly.Python.ORDER_ATOMIC);
    let branch = Blockly.Python.statementToCode(block, 'DO');
    branch = Blockly.Python.addLoopTrap(branch, block.id) || Blockly.Python.PASS;
    const code = 'while ' + text_1 + ':\n' + branch;
    return code;
};

Blockly.Python['create_actor'] = function (block) {
    var value_name = Blockly.Python.valueToCode(block, 'NAME', Blockly.Python.ORDER_ATOMIC);
    var code = 'board.create_chick(' + value_name + ')\n';
    return [code, Blockly.Python.ORDER_ATOMIC];
};

Blockly.Python['move_actor_to_row_column'] = function (block) {
    var actor = Blockly.Python.valueToCode(block, 'ACTOR', Blockly.Python.ORDER_ATOMIC);
    var row = Blockly.Python.valueToCode(block, 'ROW', Blockly.Python.ORDER_ATOMIC);
    var column = Blockly.Python.valueToCode(block, 'COLUMN', Blockly.Python.ORDER_ATOMIC);
    return actor + '.move_to(' + row + ',' + column + ')\n';
};

Blockly.Python['show_actor'] = function (block) {
    var actor = Blockly.Python.valueToCode(block, 'NAME', Blockly.Python.ORDER_ATOMIC);
    return actor + '.show()\n';
};

Blockly.Python['sleep'] = function (block) {
    var seconds = Blockly.Python.valueToCode(block, 'SECONDS', Blockly.Python.ORDER_ATOMIC);
    return 'sleep(' + seconds * 1000 + ')\n';
};
