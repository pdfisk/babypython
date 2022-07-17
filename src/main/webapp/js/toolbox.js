Blockly.Toolboxes ||= new Object(null);

Blockly.Toolboxes.STANDARD_TOOLBOX = {
    "kind": "categoryToolbox",
    "contents": [{
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
    }, {
        "kind": "CATEGORY",
        "contents": [{
            "kind": "BLOCK",
            "type": "text"
        }],
        "name": "Text",
        "colour": "#5ba58c"
    }, {
        "kind": "CATEGORY",
        "name": "Variables",
        "colour": "#a55b80",
        "custom": "VARIABLE"
    }]
};
