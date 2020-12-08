import React, { createRef, useContext } from 'react'
import XMLEditor from 'components/XMLEditor'
import { ResourceContext } from 'providers/ResourceContext.jsx'

export default function ResourceManagementEditor(props) {
  const cmRef = createRef()
  const { state, handlers } = useContext(ResourceContext)

  return (
    <div>
      <span
        style={{
          marginBottom: '5px',
          width: '31px',
          height: '35px',
          paddingLeft: '4px',
        }}
        title="Save File"
        //onClick={saveHandler}
        className="btn btn-primary btn-sm"
      >
        <i
          style={{
            marginTop: '6px',
          }}
          className="fa fa-floppy-o fa-fw fa-lg"
        />
      </span>
      <span
        title="Open resource in browser"
        onClick={handlers.openRes}
        style={{
          height: '35px',
          cursor: 'pointer',
          marginBottom: '5px',
          marginLeft: '5px',
          width: '31px',
        }}
        className="btn btn-primary btn-sm"
      >
        <i
          className="fa fa-lg fa-location-arrow fa-inverse"
          style={{
            marginTop: '7px',
          }}
        />
      </span>

      <span
        title="Theme"
        style={{
          height: '35px',
          cursor: 'pointer',
          marginLeft: '5px',
          marginBottom: '5px',
          width: '31px',
          paddingLeft: '9px',
        }}
        onClick={handlers.toggleThemeAndUpdateLStorage}
        className="btn btn-primary"
      >
        {state.darkTheme ? (
          <i className="fa fa-lg fa-moon-o" />
        ) : (
          <i className="fa fa-lg fa-lightbulb-o" />
        )}
      </span>
      <div className="XMLInput mt-2">
        <XMLEditor
          ref={cmRef}
          documentModified={state.documentModified}
          onTextChange={handlers.onTextChange}
          value={state.value}
          darkTheme={state.darkTheme}
        />
      </div>
    </div>
  )
}
