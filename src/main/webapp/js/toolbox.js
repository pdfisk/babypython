Blockly.Toolboxes = new Object(null);

Blockly.Toolboxes.STANDARD_TOOLBOX = {
    "kind": "categoryToolbox",
    "contents": [{
        "kind": "CATEGORY",
        "name": "Variables",
        "colour": "#a55b80",
        "custom": "VARIABLE"
    }, {
        "kind": "CATEGORY",
        "contents": [{
            "kind": "BLOCK",
            "type": "math_number"
        }, {
            "kind": "BLOCK",
            "type": "text"
        }],
        "name": "Literal Values",
        "colour": "#5ba58c"
    }, {
        "kind": "CATEGORY",
        "contents": [{
            "kind": "BLOCK",
            "type": "math_arithmetic"
        }],
        "name": "Calculations",
        "colour": "#5ba58c"
    }, {
        "kind": "CATEGORY",
        "contents": [{
            "kind": "BLOCK",
            "type": "logic_compare"
        }],
        "name": "Logic",
        "colour": "#5ba58c"
    }, {
        "kind": "CATEGORY",
        "contents": [{
            "kind": "BLOCK",
            "type": "create_chick"
        }, {
            "kind": "BLOCK",
            "type": "show_actor"
        }, {
            "kind": "BLOCK",
            "type": "move_actor_to_x_y"
        }, {
            "kind": "BLOCK",
            "type": "sleep"
        }],
        "name": "Actors",
        "colour": "#9fa55b"
    }]
};