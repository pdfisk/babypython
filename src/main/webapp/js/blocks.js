Blockly.Blocks['create_chick'] = {
    init: function () {
        this.appendValueInput("NAME")
            .setCheck("String")
            .appendField("create chick");
        this.setInputsInline(false);
        this.setOutput(true, null);
        this.setColour(65);
        this.setTooltip("Add the chick's name");
        this.setHelpUrl("");
    }
};

Blockly.Blocks['move_actor_to_x_y'] = {
    init: function () {
        this.appendValueInput("ACTOR")
            .appendField("move");
        this.appendValueInput("X")
            .appendField("x");
        this.appendValueInput("Y")
            .appendField("y");
        this.setInputsInline(true);
        this.setPreviousStatement(true, null);
        this.setNextStatement(true, null);
        this.setOutput(false, null);
        this.setColour(65);
        this.setTooltip("Move actor to x, y");
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
