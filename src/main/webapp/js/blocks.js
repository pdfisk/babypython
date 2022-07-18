Blockly.Blocks['controls_while'] = {
    init: function () {
        this.appendDummyInput()
            .appendField('while');
        this.appendValueInput("cond")
            .setCheck("Boolean");
        this.appendDummyInput()
            .appendField(':');
        this.appendStatementInput('DO')
            .appendField('');
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
        this.setColour("#644A9E");
        this.setTooltip('Add loop condition and statements.');
        this.setHelpUrl('');
    }
};

Blockly.Blocks['create_actor'] = {
    init: function () {
        this.appendValueInput("NAME")
            .setCheck("String")
            .appendField("create actor");
        this.appendDummyInput()
            .appendField(new Blockly.FieldDropdown([
                ["Chick", "chick"],
                ["Hen", "hen"],
                ["Dog", "dog"],
                ["Duck", "duck"]
            ]), "actor_type");
        this.setInputsInline(false);
        this.setOutput(true, null);
        this.setColour(65);
        this.setTooltip("Add the chick's name");
        this.setHelpUrl("");
    }
};

Blockly.Blocks['move_actor_to_row_column'] = {
    init: function () {
        this.appendValueInput("ACTOR")
            .appendField("move");
        this.appendValueInput("ROW")
            .appendField("row");
        this.appendValueInput("COLUMN")
            .appendField("column");
        this.setInputsInline(true);
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
        this.setOutput(false, null);
        this.setColour(65);
        this.setTooltip("Move actor to row, column");
        this.setHelpUrl("");
    }
};

Blockly.Blocks['show_actor'] = {
    init: function () {
        this.appendValueInput("NAME")
            .appendField("show");
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
        this.setOutput(false, null);
        this.setColour(65);
        this.setTooltip("Show actor on stage");
        this.setHelpUrl("");
    }
};

Blockly.Blocks['sleep'] = {
    init: function () {
        this.appendValueInput("SECONDS")
            .appendField("sleep");
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
        this.setOutput(false, null);
        this.setColour(65);
        this.setTooltip("Sleep for number of seconds");
        this.setHelpUrl("");
    }
};