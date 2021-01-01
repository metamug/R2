/* eslint-disable react/display-name */
import cm from 'codemirror'
import 'codemirror/addon/comment/comment'
import 'codemirror/addon/dialog/dialog'
import 'codemirror/addon/dialog/dialog.css'
import 'codemirror/addon/fold/brace-fold'
import 'codemirror/addon/fold/comment-fold'
import 'codemirror/addon/fold/foldcode'
import 'codemirror/addon/fold/foldgutter'
import 'codemirror/addon/fold/foldgutter.css'
import 'codemirror/addon/fold/indent-fold'
import 'codemirror/addon/fold/xml-fold'
import 'codemirror/addon/hint/show-hint'
import 'codemirror/addon/hint/show-hint.css'
import 'codemirror/addon/hint/xml-hint'
import 'codemirror/addon/scroll/annotatescrollbar'
import 'codemirror/addon/search/matchesonscrollbar'
import 'codemirror/addon/search/matchesonscrollbar.css'
import 'codemirror/addon/search/search'
import 'codemirror/addon/search/searchcursor'
import 'codemirror/keymap/sublime'
import 'codemirror/lib/codemirror.css'
import 'codemirror/theme/material.css'
import 'codemirror/theme/neat.css'
import React, { forwardRef, useEffect, useState, useContext } from 'react'
import Codemirror from 'react-codemirror'

import 'codemirror/mode/xml/xml'

import 'codemirror/addon/display/placeholder'
import { cmTags } from 'constants/xml-editor'
import { ResourceContext } from 'providers/ResourceContext.jsx'

const XMLEditor = forwardRef(
  ({ value, onTextChange, darkTheme, ...props }, ref) => {
    const completeAfter = (editor, pred) => {
      // const codeMirror = refs['xmlEditor'].getCodeMirrorInstance();
      if (!pred || pred())
        setTimeout(() => {
          if (!editor.state.completionActive)
            cm.showHint(editor, cm.hint.xml, {
              schemaInfo: cmTags,
            })
        }, 100)
      return cm.Pass
    }

    const { state, handlers } = useContext(ResourceContext)
    const [key, setKey] = useState(0)

    const completeIfAfterLt = (editor) => {
      // const codeMirror = refs['xmlEditor'].getCodeMirrorInstance();
      return completeAfter(editor, () => {
        const cur = editor.getCursor()
        return editor.getRange(cm.Pos(cur.line, cur.ch - 1), cur) === '<'
      })
    }

    const completeIfInTag = (editor) => {
      return completeAfter(editor, () => {
        const tok = editor.getTokenAt(editor.getCursor())
        if (
          tok.type === 'string' &&
          (!/['"]/.test(tok.string.charAt(tok.string.length - 1)) ||
            tok.string.length === 1)
        )
          return false
        const inner = cm.innerMode(editor.getMode(), tok.state).state
        return inner.tagName
      })
    }

    const cmAutoComplete = (editor) => {
      // const codeMirror = refs['xmlEditor'].getCodeMirrorInstance();
      cm.showHint(editor, cm.hint.xml, { schemaInfo: cmTags })
    }
    const toggleComment = (cmInstance) => {
      cmInstance.toggleComment({ indent: true })
    }

    const indentToggle = (cmInstance) => {
      cmInstance.execCommand('indentAuto')
    }

    const codemirrorOptions = {
      lineNumbers: true,
      autoCloseBrackets: true,
      mode: 'xml',
      autofocus: true,
      placeholder: `Type here...  \n \n Press Ctrl+S to save`,
      keyMap: 'sublime',
      foldGutter: true,
      inputStyle: 'contenteditable',
      gutters: ['CodeMirror-linenumbers', 'CodeMirror-foldgutter'],
      extraKeys: {
        "'<'": completeAfter,
        "'/'": completeIfAfterLt,
        "' '": completeIfInTag,
        "'='": completeIfInTag,
        'Ctrl-Space': cmAutoComplete,
        'Ctrl-/': toggleComment,
        F12: indentToggle,
        // "Ctrl-Q": function(cm){cm.foldCode(cm.getCursor())}
      },
    }

    useEffect(() => {
      setKey(key + 1)
    }, [state.selectedResource])

    return (
      <div>
        <Codemirror
          options={{
            ...codemirrorOptions,
            theme: darkTheme ? 'material' : 'neat',
          }}
          {...props}
          key={key}
          onChange={onTextChange}
          autoFocus
          ref={ref}
          value={value}
        />
      </div>
    )
  }
)

export default XMLEditor
