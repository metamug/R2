import React from 'react'
import { Button, Modal } from 'react-bootstrap'

export default function ErrorModal(props) {
  const { state, dispatch } = props

  const { open, message } = state

  return (
    <Modal
      show={open}
      aria-labelledby="Error Modal"
      backdrop="static"
      onHide={() => dispatch({ type: 'close' })}
      centered
    >
      <Modal.Header closeButton>
        <h4
          className="modal-title"
          style={{ fontWeight: 'bold', fontSize: '17px' }}
        >
          Error!
        </h4>
      </Modal.Header>
      <Modal.Body>{message}</Modal.Body>
      <Modal.Footer>
        <Button
          type="button"
          variant="danger"
          onClick={() => dispatch({ type: 'close' })}
        >
          Close
        </Button>
      </Modal.Footer>
    </Modal>
  )
}
