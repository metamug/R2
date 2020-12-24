import { createNewResource, fetchXML } from 'api/apis'
import XMLEditor from 'components/XMLEditor'
import XMLEditor2 from 'components/XMLEditor2'
import { API_URL_DOMAIN, newResourceDefaultVal } from 'constants/resource'
import React, { createRef, useEffect, useState } from 'react'
import { Breadcrumb, Form, Modal, Row } from 'react-bootstrap'
import { Link } from 'react-router-dom'
import { getParams } from 'utils/queryParams'
import { saveResourceXML } from 'api/apis'
import { useLoadingContext } from 'providers/LoadingContext'
import { useErrorModalContext } from 'providers/ErrorModalContext'

const newResourceTitle = 'New Resource'

const initialResource = {
  name: newResourceTitle,
  version: '1.0',
  isNewResource: true,
}

function ResourceEditor(props) {
  const cmRef = createRef()

  const [loading, setLoading] = useLoadingContext()
  const [error, setError] = useErrorModalContext()

  const [connectionError, setConnectionError] = useState(false)
  const [darkTheme, setDarkTheme] = useState(false)
  const [isNew, setIsNew] = useState(false)
  const [savedValue, setSavedValue] = useState('')
  const [value, setValue] = useState(newResourceDefaultVal)
  const [documentModified, setDocumentModified] = useState(false)
  const [nameModalOpen, setNameModalOpen] = useState(false)
  const [saving, setSaving] = useState(false)
  const [resNameError, setResNameError] = useState(false)
  const [emptyWarningModal, setEmptyWarningModal] = useState(false)
  // const [error, setError] = useState(false)
  const [errorMessage, setErrorMessage] = useState('')
  const [savedOnce, setSavedOnce] = useState(false)
  const [selectedResource, setSelectedResource] = useState(initialResource)
  const [newResourceName, setNewResourceName] = useState('')
  const [xmlResponse, setXmlResponse] = useState('')
  const [xmlUpdated, setXmlUpdated] = useState(false)

  const { name, version, isNewResource } = selectedResource

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

  const handleNewNameChange = (event) => setNewResourceName(event.target.value)

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

  const saveResource = async () => {
    try {
      setLoading({
        type: 'open',
        payload: { message: `Saving Resource...` },
      })
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
  const overrideSave = (e) => {
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
  }, [])

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
    getXMLForSelectedResource()

    window.addEventListener('keydown', overrideSave)

    // cleanup this component
    return () => {
      window.removeEventListener('beforeunload', onBeforeUnload)
      window.removeEventListener('keydown', overrideSave)
    }
  }, [selectedResource.version])

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
    <div className="container" style={{ marginTop: '10px' }}>
      {console.log(props)}
      <Row>
        <Breadcrumb style={{ marginLeft: '15px' }}>
          <Breadcrumb.Item>
            <Link to="/resources">resources</Link>
          </Breadcrumb.Item>
          <Breadcrumb.Item active>
            {isNewResource ? newResourceTitle : name}
          </Breadcrumb.Item>
        </Breadcrumb>
        <div className="row pt-4 ml-1">
          {connectionError ? (
            <div>
              <h5 className="text text-danger">
                Unable to connect to server. Please check your connection!
              </h5>
            </div>
          ) : (
            <div style={{ marginTop: '-1.2em' }} className="col-md-12">
              <div className="row" style={{ marginLeft: '3px' }}>
                <span
                  style={{
                    marginBottom: '5px',
                    width: '31px',
                    paddingLeft: '4px',
                  }}
                  title="Save File"
                  onClick={
                    isNewResource ? () => setNameModalOpen(true) : saveHandler
                  }
                  className="btn btn-primary btn-sm"
                >
                  <i
                    style={{
                      marginTop: '6px',
                    }}
                    className="fa fa-floppy-o fa-fw fa-lg"
                  />
                </span>
                {!isNewResource && (
                  <span
                    title="Open resource in browser"
                    onClick={openRes}
                    style={{
                      // height: '29px',
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
                )}
                <span
                  title="Theme"
                  style={{
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
              </div>
            </div>
          )}
          <Modal
            show={nameModalOpen}
            contentLabel="resource-name-modal"
            onHide={() => setNameModalOpen(false)}
          >
            <Modal.Header closeButton>
              <h4 className="modal-title">Enter resource name</h4>
            </Modal.Header>
            <Modal.Body>
              <Form.Control
                type="text"
                name="resourceName"
                // onKeyPress={handleSaveKeyPress}
                onChange={handleNewNameChange}
                autoFocus
                required
              />
              {resNameError ? (
                <p className="text text-danger">
                  Spaces/special characters/capital letters/numbers not allowed!
                </p>
              ) : null}
            </Modal.Body>
            <Modal.Footer>
              <button
                type="submit"
                className="btn btn-primary pull-right"
                onClick={saveHandler}
              >
                Save
              </button>
            </Modal.Footer>
          </Modal>

          <Modal
            contentLabel="empty-warning-modal"
            show={emptyWarningModal}
            className="col-md-4"
          >
            <div className="modal-header">
              <h4>Warning</h4>
            </div>
            <div className="modal-body">
              <p>Please enter valid xml text!</p>
            </div>
            <div className="modal-footer">
              <button
                type="submit"
                className="btn btn-warning"
                onClick={() => setEmptyWarningModal(false)}
              >
                OK
              </button>
            </div>
          </Modal>

          <Modal
            contentLabel="resource-saving-modal"
            // onRequestClose={resetState}
            show={saving}
            className="col-md-4"
          >
            <div className="modal-body">
              {isNew ? (
                savedOnce ? (
                  <h4>
                    Saving resource...
                    <span
                      style={{ color: '#34495e' }}
                      className="fa fa-circle-o-notch fa-spin pull-right"
                    />
                  </h4>
                ) : (
                  <h4>
                    Creating resource...
                    <span
                      style={{ color: '#34495e' }}
                      className="fa fa-circle-o-notch fa-spin pull-right"
                    />
                  </h4>
                )
              ) : (
                <h4>
                  Saving resource...
                  <span
                    style={{ color: '#34495e' }}
                    className="fa fa-circle-o-notch fa-spin pull-right"
                  />
                </h4>
              )}
            </div>
          </Modal>
        </div>
      </Row>
      <div className="XMLInput">
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

export default ResourceEditor
