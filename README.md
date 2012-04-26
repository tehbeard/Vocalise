#Vocalise

##About Vocalise

Vocalise is a project to provide a set of tools for people using the Conversation API in bukkit.  

## Why this project came about

While the conversation API has provided a very powerful mechanism for developers, both for allowing "conversation"
with entities as well as guided configuration for plugins, the lack of concrete implmentations for standard prompts
such as messages offers great flexibility and complexity.

This is where Vocalise comes in. Vocalise is a set of classes that provide Concrete versions of the various prompts
in order to speed up coding of the static parts of a conversation graph.

Vocalise also provides `PromptBuilder()`. PromptBuilder parses Yaml files (either in the file system or inside the jar
using the `getResource(String)` function, creating a prompt graph.
Currently PromptBuilder supports a number of internal Prompt types (see config.md). Custom prompts can be passed into 
PromptBuilder, so they can be used by a script. Support is provided as well to load your own Prompt classes.

##Project Goals

* Provide a series of high level prompts that can be utilised by other developers to create rich interfaces  
with users.

* A mechanism to allow complex prompt graphs to be constructed from a file, rather then by large amounts of  
boilerplate code.


##License
Copyright (C) 2012 James "Tehbeard" Holt

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated  
documentation files (the "Software"), to deal in the Software without restriction, including without limitation  
the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and  
to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of  
the Software.  

The name of the copyright holder may not be used to endorse or promote products derived from this software without  
specific prior written permission.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO  
THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE  
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,  
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.