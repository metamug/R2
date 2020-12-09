import XMLEditor from 'components/XMLEditor'
import { useResourceContext } from 'providers/ResourceContext.jsx'
import React, { useEffect, useState } from 'react'
import { Breadcrumb, Form, Modal, Row } from 'react-bootstrap'
import { Link } from 'react-router-dom'
import { getParams } from 'utils/queryParams'

function ResourceEditor(props) {
  const [state, handlers, refs] = useResourceContext()

  const [connectionError, setConnectionError] = useState(false)
  const [isNew, setIsNew] = useState(false)
  const [saving, setSaving] = useState(false)
  const [resNameError, setResNameError] = useState(false)
  const [emptyWarningModal, setEmptyWarningModal] = useState(false)
  const [savedOnce, setSavedOnce] = useState(false)

  const overrideSave = (e) => {
    const { isNew } = getParams(props.location.search)
    if (
      (window.navigator.platform.match('Mac') ? e.metaKey : e.ctrlKey) &&
      e.keyCode == 83
    ) {
      e.preventDefault()
      if (isNew === 'true') {
        handlers.setNameModalOpen(true)
      } else {
        handlers.saveResource(e)
      }
      // Process the event here (such as click on submit button)
    }
  }

  useEffect(() => {
    window.addEventListener('keydown', overrideSave)
    // cleanup this component
    return () => {
      window.removeEventListener('beforeunload', handlers.onBeforeUnload)
      window.removeEventListener('keydown', overrideSave)
    }
  }, [handlers.trimAsteriskFromTitle(state.selectedResource.name)])

  useEffect(() => {
    const { name, version, isNew } = getParams(props.location.search)
    handlers.setSelectedResource({
      name: isNew === 'true' ? handlers.newResourceTitle : name,
      version: version,
      isNewResource: isNew === 'true',
    })
    handlers.setDarkTheme(localStorage.darkMode)
  }, [])

  return (
    <div className="container" style={{ marginTop: '10px' }}>
      <Row>
        <Breadcrumb style={{ marginLeft: '15px' }}>
          <Breadcrumb.Item>
            <Link to="/resources">resources</Link>
          </Breadcrumb.Item>
          <Breadcrumb.Item active>
            {state.isNewResource
              ? state.newResourceTitle
              : state.selectedResource.name}
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
                    state.isNewResource
                      ? () => handlers.setNameModalOpen(true)
                      : handlers.saveHandler
                  }
                  className="btn btn-primary btn-sm"
                >
                  <i
                    style={{
                      marginTop: '6px',
                    }}
                    className="fa fa-floppy-o fa-fw fa-lg"
                  />
                  {/* <span
                    style={{
                      marginTop: '6px',
                    }}
                  >
                    {isNewResource ? newResourceTitle : name}
                  </span>*/}
                </span>
                {!state.isNewResource && (
                  <span
                    title="Open resource in browser"
                    onClick={handlers.openRes}
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
                  onClick={handlers.toggleThemeAndUpdateLStorage}
                  className="btn btn-primary"
                >
                  {state.darkTheme ? (
                    <i className="fa fa-lg fa-moon-o" />
                  ) : (
                    <i className="fa fa-lg fa-lightbulb-o" />
                  )}
                </span>
              </div>
            </div>
          )}

          {/* <span
                        className="custom-control custom-checkbox"
                        style={{ marginLeft: '220px', marginTop: '3px' }}
                      >
                        <input
                          type="checkbox"
                          onClick={() =>
                            setState({ iframe: !iframe })
                          }
                          className="custom-control-input"
                          id="customCheck1"
                          style={{ height: '30px' }}
                        />
                        <label
                          className="custom-control-label"
                          htmlFor="customCheck1"
                        >
                          Docs
                        </label>
                      </span> */}
          <Modal
            show={state.nameModalOpen}
            contentLabel="resource-name-modal"
            onHide={() => handlers.setNameModalOpen(false)}
          >
            <Modal.Header closeButton>
              <h4 className="modal-title">Enter resource name</h4>
            </Modal.Header>
            <Modal.Body>
              <Form.Control
                type="text"
                name="resourceName"
                // onKeyPress={handleSaveKeyPress}
                onChange={handlers.handleNewNameChange}
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
                onClick={handlers.saveHandler}
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

          {/* <Modal
            show={error}
            className="col-md-4"
            contentLabel="resource-error-modal"
          >
            <div>
              <div className="modal-header">
                <h4>
                  Error saving resource&nbsp;
                  <span className="text-success">{name}</span>
                </h4>
              </div>
              <div className="modal-body">
                <div
                  dangerouslySetInnerHTML={{
                    __html: errorMessage,
                  }}
                />
              </div>
              <div className="modal-footer">
                <button
                  type="submit"
                  className="btn btn-primary"
                  //   onClick={resetState}
                >
                  OK
                </button>
              </div>
            </div>
          </Modal> */}
        </div>
        {/* {!gettingData && iframe ? (
            <div className="col-md-5">
              <iframe
                title="Documentation"
                id="inlineFrame"
                style={{ border: '1px solid #3c763d' }}
                width="500"
                height="350"
                // src="/dummy/docs/v1.0/res.html"
                src={`${API_URL_DOMAIN}/${localStorage.defaultItem}/docs/${version}/${name}.html`}
              />
            </div>
          ) : null} */}
      </Row>
      <div className="XMLInput">
        <XMLEditor
          ref={refs.cmRef}
          documentModified={state.documentModified}
          onTextChange={handlers.onTextChange}
          value={state.value}
          darkTheme={state.darkTheme}
        />
      </div>
    </div>
  )
}

export default ResourceEditor
