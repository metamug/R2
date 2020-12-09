import { createNewResource, fetchXML, saveResourceXML } from 'api/apis'
import { API_URL_DOMAIN, newResourceDefaultVal } from 'constants/resource'
import { useErrorModalContext } from 'providers/ErrorModalContext'
import { useLoadingContext } from 'providers/LoadingContext'
import React, {
  createContext,
  createRef,
  useContext,
  useEffect,
  useState,
} from 'react'

const ResourceContext = createContext()

const newResourceTitle = 'New Resource'

const initialResource = {
  name: newResourceTitle,
  version: '1.0',
  isNewResource: true,
}

export default function ResourceProvider(props) {
  const cmRef = createRef()

  const [loading, setLoading] = useLoadingContext()
  const [error, setError] = useErrorModalContext()

  const [darkTheme, setDarkTheme] = useState(true)
  const [selectedResource, setSelectedResource] = useState(initialResource)
  const [nameModalOpen, setNameModalOpen] = useState(false)
  const [documentModified, setDocumentModified] = useState(false)
  const [value, setValue] = useState(newResourceDefaultVal)
  const [savedValue, setSavedValue] = useState('')
  const [newResourceName, setNewResourceName] = useState('')
  const [xmlResponse, setXmlResponse] = useState('')
  const [xmlUpdated, setXmlUpdated] = useState(false)

  const { name, version, created, isNewResource } = selectedResource

  const openRes = () => {
    const url = `${API_URL_DOMAIN}/${localStorage.defaultItem}/${version}/${name}`
    window.open(url, '_blank')
  }

  const trimAsteriskFromTitle = (name) => {
    return name.replace(/\*/g, '')
  }

  const handleNewNameChange = (event) => setNewResourceName(event.target.value)

  const getModifiedTitle = (name) => {
    return `${name}*`
  }

  const onBeforeUnload = (ev) => {
    // eslint-disable-next-line no-param-reassign
    ev.returnValue = 'Are you sure you want to leave this page?'
  }

  const onTextChange = (text) => {
    if (!documentModified) {
      window.addEventListener('beforeunload', onBeforeUnload)
      document.title += ' *'
      setDocumentModified(true)
      setSelectedResource({
        ...selectedResource,
        name: getModifiedTitle(selectedResource.name),
      })
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

  const toggleThemeAndUpdateLStorage = () => {
    setDarkTheme((prevState) => {
      const newState = !prevState
      localStorage.darkMode = newState
      return newState
    })
  }

  const getXMLForSelectedResource = async () => {
    if (!isNewResource) {
      try {
        setLoading({
          type: 'open',
          payload: { message: `Getting resource data...` },
        })
        const { data } = await fetchXML(name, version)
        setXmlResponse(data)
        setSavedValue(data)
        setValue(data)
        return setLoading({ type: 'close' })
      } catch (error) {
        setLoading({ type: 'close' })
        return setError({
          type: 'open',
          payload: { message: 'Could not get resource data' },
        })
      }
      // cmRef.current && cmRef.current.getCodeMirror().setValue(value)
    }
  }

  const createResource = async () => {
    if (/^[a-z]+$/.test(newResourceName)) {
      setLoading({
        type: 'open',
        payload: { message: `Creating Resource...` },
      })
      try {
        await createNewResource(newResourceName, value)
        setLoading({ type: 'close' })
        props.history.push('/resources')
      } catch (error) {
        setLoading({ type: 'close' })
        return setError({
          type: 'open',
          payload: {
            message: error.message || 'Failed to create resource',
          },
        })
      }
    } else {
      return setError({
        type: 'open',
        payload: { message: 'Invalid resource name provided' },
      })
    }
  }

  const saveHandler = (e) => {
    e.preventDefault()
    if (value !== '') {
      if (isNewResource) {
        return createResource()
      }
      isSameVersion() ? saveResource() : createResource()
    } else {
      return setError({
        type: 'open',
        payload: { message: 'Resource XML cannot be empty' },
      })
    }
  }

  const saveResource = async () => {
    try {
      setLoading({
        type: 'open',
        payload: { message: `Saving Resource...` },
      })
      console.log(value)
      debugger
      await saveResourceXML(trimAsteriskFromTitle(name), version, value)
      setDocumentModified(false)
      setSelectedResource({
        ...selectedResource,
        name: trimAsteriskFromTitle(selectedResource.name),
      })
      setLoading({ type: 'close' })
      window.removeEventListener('beforeunload', onBeforeUnload)
    } catch (error) {
      setLoading({ type: 'close' })
      return setError({
        type: 'open',
        payload: { message: error.message || 'Failed to save resource' },
      })
    }
  }

  /*const overrideSave = (e) => {
    const { isNew } = getParams(props.location.search)
    if (
      (window.navigator.platform.match('Mac') ? e.metaKey : e.ctrlKey) &&
      e.keyCode == 83
    ) {
      e.preventDefault()
      if (isNew === 'true') {
        setNameModalOpen(true)
      } else {
        saveResource(e)
      }
      // Process the event here (such as click on submit button)
    }
  }

  useEffect(() => {
    const { name, version, isNew } = getParams(props.location.search)
    setSelectedResource({
      name: isNew === 'true' ? newResourceTitle : name,
      version: version,
      isNewResource: isNew === 'true',
    })
    setDarkTheme(localStorage.darkMode)
  }, [])*/

  useEffect(() => {
    debugger
    getXMLForSelectedResource()
  }, [trimAsteriskFromTitle(selectedResource.name)])

  useEffect(() => {
    // getXMLForSelectedResource()
    if (cmRef.current && xmlResponse !== '') {
      // const cmValue = cmRef.current.getCodeMirror().getValue()
      // if (cmValue !== xmlResponse) return
      if (!xmlUpdated) {
        cmRef.current.getCodeMirror().setValue(xmlResponse)
        setXmlUpdated(true)
      }
    }
  }, [cmRef])

  useEffect(() => {
    document.title = name
  }, [name])

  useEffect(() => {
    if (value === savedValue) {
      window.removeEventListener('beforeunload', onBeforeUnload)
      setDocumentModified(false)
      setSelectedResource({
        ...selectedResource,
        name: trimAsteriskFromTitle(name),
      })
    }
  }, [value])

  return (
    <ResourceContext.Provider
      value={{
        state: {
          darkTheme,
          selectedResource,
          loading,
          error,
          nameModalOpen,
          documentModified,
          value,
          savedValue,
          isNewResource,
          newResourceTitle,
        },
        handlers: {
          setDarkTheme,
          setSelectedResource,
          setLoading,
          setError,
          setNameModalOpen,
          setDocumentModified,
          setSavedValue,
          setValue,
          toggleThemeAndUpdateLStorage,
          onTextChange,
          openRes,
          saveHandler,
          saveResource,
          getXMLForSelectedResource,
          onBeforeUnload,
          newResourceTitle,
          handleNewNameChange,
          trimAsteriskFromTitle,
        },
        refs: { cmRef },
      }}
    >
      {props.children}
    </ResourceContext.Provider>
  )
}

function useResourceContext() {
  const context = useContext(ResourceContext)
  if (context === undefined) {
    throw new Error('Context Unavailable')
  }
  return [context.state, context.handlers, context.refs]
}

export { ResourceContext, useResourceContext }
