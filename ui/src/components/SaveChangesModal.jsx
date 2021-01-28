import React from 'react'
import { Button, Modal } from 'react-bootstrap'

export default function SaveChangesModal(props) {
  const { state, dispatch } = props
  const { open, message } = state

  return (
    <Modal
      show={open}
      aria-labelledby="SaveChanges Modal"
      backdrop="static"
      onHide={() => dispatch({ type: 'close' })}
      centered
    >
      <Modal.Header closeButton>
        <h4
          className="modal-title"
          style={{ fontWeight: 'bold', fontSize: '17px' }}
        >
          Warning!
        </h4>
      </Modal.Header>
      <Modal.Body>{message}</Modal.Body>
      <Modal.Footer>
        <Button
          type="button"
          variant="primary"
          onClick={() => {
            console.log('save is clicked'), dispatch({ type: 'close' })
          }}
        >
          Save
        </Button>
        <Button
          type="button"
          variant="danger"
          onClick={() => {
            console.log('continue is clicked'), dispatch({ type: 'close' })
          }}
        >
          Continue
        </Button>
      </Modal.Footer>
    </Modal>
  )
}
