#Vocalise format

##Available prompts

* Message Block
* Boolean Prompt
* Menu Prompt

##Format

Each prompt contains at the very least it's `type:`

Additionally a prompt may have an `id:` that uniquely identifies that prompt, for the purpose of non-linear prompt graphs

Prompt configurations must be in-line, if referencing a previous prompt, instead use *id of a prompt definition where id is the value of `id:` of the prompt to jump to.

use *NULL to indicate end of conversation

###Message prompt

Displays a message to the user then moves to the next prompt

#####Code Sample
    type:msg
    id:optionalId
    text:"This is the message to display, &ecolor codes are accepted"
    next: nested definition of next prompt, or "*id"

###Boolean prompt

Displays a boolean prompt to the user then moves to a specific prompt based on input

#####Code Sample
    type:bool
    id:optionalId
    text:"Do you wish to continue?"
    t: nested definition of prompt to goto if true, or "*id"
    f: nested definition of prompt to goto if false, or "*id"

###Menu prompt

Displays a Menu for the user to select from

#####Code Sample
    type:bool
    id:optionalId
    text:"Do you wish to continue?"
    options:
      '0':
        name:"menuOption1"
        prompt:nested definition of prompt, or "*id"
      '0':
        name:"menuOption2"
        prompt:nested definition of prompt, or "*id"
      .....