Blockly.Python['create_chick'] = function (block) {
    var value_name = Blockly.Python.valueToCode(block, 'NAME', Blockly.Python.ORDER_ATOMIC);
    var code = 'stage.create_chick(' + value_name + ')\n';
    return [code, Blockly.Python.ORDER_ATOMIC];
};

Blockly.Python['move_actor_to_x_y'] = function (block) {
    var actor = Blockly.Python.valueToCode(block, 'ACTOR', Blockly.Python.ORDER_ATOMIC);
    var x = Blockly.Python.valueToCode(block, 'X', Blockly.Python.ORDER_ATOMIC);
    var y = Blockly.Python.valueToCode(block, 'Y', Blockly.Python.ORDER_ATOMIC);
    return actor + '.move_to(' + x + ',' + y + ')\n';
};

Blockly.Python['show_actor'] = function (block) {
    var actor = Blockly.Python.valueToCode(block, 'NAME', Blockly.Python.ORDER_ATOMIC);
    return actor + '.show()\n';
};

Blockly.Python['sleep'] = function (block) {
    var seconds = Blockly.Python.valueToCode(block, 'SECONDS', Blockly.Python.ORDER_ATOMIC);
    return 'sleep(' + seconds * 1000 + ')\n';
};
