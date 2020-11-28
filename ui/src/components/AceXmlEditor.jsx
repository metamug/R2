import { newResourceDefaultVal } from 'constants/resource'
import React, { useEffect } from 'react'
import AceEditor from 'react-ace'
import 'ace-builds/src-noconflict/mode-xml'
import 'ace-builds/src-noconflict/theme-tomorrow'
import * as langTools from 'ace-builds/src-noconflict/ext-language_tools'
import 'ace-builds/src-noconflict/ext-options'

function AceXmlEditor() {
  useEffect(() => {
    var staticWordCompleter = {
      getCompletions: function (editor, session, pos, prefix, callback) {
        var wordList = ['<foo', 'bar', 'baz']
        callback(
          null,
          wordList.map(function (word) {
            return {
              caption: word,
              value: word,
              meta: 'static',
            }
          })
        )
      },
    }

    langTools.setCompleters([staticWordCompleter])
  })
  return (
    <AceEditor
      //   placeholder={newResourceDefaultVal}
      mode="xml"
      theme="tomorrow"
      name="blah2"
      //   onLoad={this.onLoad}
      //   onChange={this.onChange}
      fontSize={14}
      showPrintMargin={true}
      showGutter={true}
      highlightActiveLine={true}
      defaultValue={newResourceDefaultVal}
      //   value={``}
      setOptions={{
        enableBasicAutocompletion: true,
        enableLiveAutocompletion: false,
        enableSnippets: true,
        showLineNumbers: true,
        tabSize: 2,
      }}
    />
  )
}

export default AceXmlEditor
