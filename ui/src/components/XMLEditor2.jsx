/* eslint-disable react/display-name */
import React, { forwardRef } from 'react'
import cm from 'codemirror'
import { Controlled as CodeMirror } from 'react-codemirror2'
import 'codemirror/lib/codemirror.css'
import 'codemirror/theme/material.css'
import 'codemirror/mode/javascript/javascript'
import 'codemirror/mode/xml/xml'
import 'codemirror/addon/hint/show-hint'
import 'codemirror/addon/hint/xml-hint'
import 'codemirror/addon/hint/show-hint.css'
import 'codemirror/keymap/sublime'
import 'codemirror/addon/edit/closebrackets'
import 'codemirror/addon/edit/closetag'
import 'codemirror/addon/fold/foldcode'
import 'codemirror/addon/fold/foldgutter'
import 'codemirror/addon/fold/brace-fold'
import 'codemirror/addon/fold/comment-fold'
import 'codemirror/addon/fold/foldgutter.css'
import 'codemirror/theme/material.css'
import 'codemirror/theme/neat.css'
import { cmTags } from 'constants/xml-editor'

const XMLEditor2 = forwardRef(
  ({ value, onTextChange, darkTheme, ...props }, ref) => {
    function completeAfter(cm, pred) {
      var cur = cm.getCursor()
      if (!pred || pred())
        setTimeout(function () {
          if (!cm.state.completionActive)
            cm.showHint({
              completeSingle: false,
            })
        }, 100)
      return CodeMirror.Pass
    }

    function completeIfAfterLt(cm) {
      return completeAfter(cm, function () {
        var cur = cm.getCursor()
        return cm.getRange(CodeMirror.Pos(cur.line, cur.ch - 1), cur) == '<'
      })
    }

    console.log(value)

    return (
      <CodeMirror
        value={value}
        ref={ref}
        options={{
          theme: darkTheme ? 'material' : 'neat',
          mode: 'xml',
          lineWrapping: true,
          smartIndent: true,
          lineNumbers: true,
          foldGutter: true,
          gutters: ['CodeMirror-linenumbers', 'CodeMirror-foldgutter'],
          autoCloseTags: true,
          keyMap: 'sublime',
          matchBrackets: true,
          autoCloseBrackets: true,
          extraKeys: {
            "'<'": completeAfter,
            "'/'": completeIfAfterLt,
            // "' '": completeIfInTag,
            // "'='": completeIfInTag,
            'Ctrl-Space': 'autocomplete',
          },
          hintOptions: {
            schemaInfo: cmTags,
          },
        }}
        onBeforeChange={(editor, data, value) => {
          onTextChange(value)
        }}
        // onUpdate={(editor) => {
        //   if (editor.getValue() !== value) {
        //     editor.setValue(value)
        //   }
        // }}
        // onChange={(editor, data, value) => {}}
      />
    )
  }
)

export default XMLEditor2
