import XMLEditor from 'components/XMLEditor'
import { API_URL_DOMAIN, newResourceDefaultVal } from 'constants/resource'
import React, { createRef, useEffect, useState, useContext } from 'react'
import { fetchXML } from 'api/apis'
import { saveResourceXML } from 'api/apis'
import { useLoadingContext } from 'providers/LoadingContext'
import { useErrorModalContext } from 'providers/ErrorModalContext'
import { useSaveChangesModalContext } from 'providers/SaveChangesModalContext'
import { ResourceContext } from 'providers/ResourceContext.jsx'

const newResourceTitle = 'New Resource'

export default function ResourceManagementEditor(props) {
  const { state, handlers } = useContext(ResourceContext)

  const cmRef = createRef()

  const [loading, setLoading] = useLoadingContext()
  const [error, setError] = useErrorModalContext()
  const [saveChanges, setSaveChanges] = useSaveChangesModalContext()
  const [connectionError, setConnectionError] = useState(false)
  const [darkTheme, setDarkTheme] = useState(false)
  const [isNew, setIsNew] = useState(false)
  const [savedValue, setSavedValue] = useState(newResourceDefaultVal)
  const [value, setValue] = useState(newResourceDefaultVal)
  const [documentModified, setDocumentModified] = useState(false)
  const [nameModalOpen, setNameModalOpen] = useState(false)
  const [saving, setSaving] = useState(false)
  const [resNameError, setResNameError] = useState(false)
  const [emptyWarningModal, setEmptyWarningModal] = useState(false)
  // const [error, setError] = useState(false)
  const [errorMessage, setErrorMessage] = useState('')
  const [savedOnce, setSavedOnce] = useState(false)
  const [newResourceName, setNewResourceName] = useState('')
  const [xmlResponse, setXmlResponse] = useState(false)
  const [xmlUpdated, setXmlUpdated] = useState(false)
  const { name, version, created, isNewResource } = state.selectedResource

  const onBeforeUnload = (ev) => {
    // eslint-disable-next-line no-param-reassign
    ev.returnValue = 'Are you sure you want to leave this page?'
  }

  const openRes = () => {
    const url = `${API_URL_DOMAIN}/${localStorage.defaultItem}/${version}/${name}`
    window.open(url, '_blank')
  }

  const getModifiedTitle = (name) => {
    return `${name}*`
  }

  const trimAsteriskFromTitle = (name) => {
    return name.replace(/\*/g, '')
  }

  const onTextChange = (text) => {
    if (!documentModified) {
      window.addEventListener('beforeunload', onBeforeUnload)
      document.title += ' *'
      setDocumentModified(true)
      return setValue(text)
    }
    return setValue(text)
  }

  const isSameVersion = () => {
    let xmlDoc
    if (window.DOMParser) {
      const parser = new DOMParser()
      xmlDoc = parser.parseFromString(value, 'text/xml')
    } else {
      // Internet Explorer
      // eslint-disable-next-line no-undef
      xmlDoc = new ActiveXObject('Microsoft.XMLDOM')
      xmlDoc.async = false
      xmlDoc.loadXML(value)
    }
    const versionFromXML = `v${xmlDoc
      .getElementsByTagName('Resource')[0]
      .getAttribute('v')}`
    return versionFromXML === version
  }

  const saveResource = async () => {
    console.log('saving is called')
    try {
      setLoading({
        type: 'open',
        payload: { message: `Saving Resource...` },
      })
      await saveResourceXML(trimAsteriskFromTitle(name), version, value)
      setDocumentModified(false)
      document.title = state.selectedResource.name
      setSavedValue(value)
      setLoading({ type: 'close' })
      handlers.setSaved(true)
      window.removeEventListener('beforeunload', onBeforeUnload)
    } catch (error) {
      setLoading({ type: 'close' })
      return setError({
        type: 'open',
        payload: { message: error.message || 'Failed to save resource' },
      })
    }
  }

  const saveHandler = (e) => {
    e.preventDefault()
    if (value !== '') {
      saveResource()
    } else {
      return setError({
        type: 'open',
        payload: { message: 'Resource XML cannot be empty' },
      })
    }
  }

  const toggleThemeAndUpdateLStorage = () => {
    setDarkTheme((prevState) => {
      const newState = !prevState
      localStorage.darkMode = newState
      return newState
    })
  }

  const getXMLForSelectedResource = async () => {
    try {
      setLoading({
        type: 'open',
        payload: { message: `Getting resource data...` },
      })
      const { data } = await fetchXML(
        trimAsteriskFromTitle(state.selectedResource.name),
        state.selectedResource.version
      )
      setXmlResponse(true)
      setSavedValue(data)
      setValue(data)
      return setLoading({ type: 'close' })
    } catch (error) {
      setLoading({ type: 'close' })
      return setError({
        type: 'open',
        payload: {
          message: 'Could not get resource data',
          saveHandler: saveHandler(),
        },
      })
    }
  }

  useEffect(() => {
    setDarkTheme(localStorage.darkMode)
  }, [])

  useEffect(() => {
    // if (cmRef.current && xmlResponse !== '') {
    //   if (!xmlUpdated) {
    //     cmRef.current.getCodeMirror().setValue(xmlResponse)
    //     setXmlUpdated(true)
    //   }
    // }
  }, [cmRef])

  useEffect(() => {
    if (trimAsteriskFromTitle(state.selectedResource.name) !== newResourceTitle)
      getXMLForSelectedResource()
    return () => {
      window.removeEventListener('beforeunload', onBeforeUnload)
    }
  }, [trimAsteriskFromTitle(state.selectedResource.name)])

  useEffect(() => {
    document.title = state.selectedResource.name
    /*if (value != savedValue) {
      //handlers.setSaved(false)
      return setSaveChanges({
        type: 'open',
        payload: { message: 'Changes not saved' },
      })
    }*/
  }, [state.selectedResource.name])

  useEffect(() => {
    if (value === savedValue) {
      handlers.setSaved(true)
      document.title = state.selectedResource.name
      window.removeEventListener('beforeunload', onBeforeUnload)
      setDocumentModified(false)
      if (xmlResponse) {
        handlers.setSelectedResource({
          ...state.selectedResource,
          name: trimAsteriskFromTitle(state.selectedResource.name),
        })
        setXmlResponse(false)
      }
    } else {
      handlers.setSaved(false)
    }
  }, [value])

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
        onClick={saveHandler}
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
        onClick={openRes}
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
        onClick={toggleThemeAndUpdateLStorage}
        className="btn btn-primary"
      >
        {darkTheme ? (
          <i className="fa fa-lg fa-moon-o" />
        ) : (
          <i className="fa fa-lg fa-lightbulb-o" />
        )}
      </span>
      <div className="XMLInput mt-2">
        <XMLEditor
          ref={cmRef}
          documentModified={documentModified}
          onTextChange={onTextChange}
          value={value}
          darkTheme={darkTheme}
        />
      </div>
    </div>
  )
}
