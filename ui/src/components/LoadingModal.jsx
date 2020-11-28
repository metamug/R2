import React from 'react'
import { Modal } from 'react-bootstrap'

export default function LoadingModal(props) {
  const { state, dispatch } = props

  const { loading, message } = state

  return (
    <Modal
      show={loading}
      aria-labelledby="Loading Modal"
      backdrop="static"
      size="sm"
      onHide={() => dispatch({ type: 'close' })}
      centered
    >
      <Modal.Body>
        {' '}
        <h6>
          {message}
          <span
            style={{ color: '#34495e' }}
            className="fa fa-circle-o-notch fa-spin pull-right"
          />
        </h6>
      </Modal.Body>
    </Modal>
  )
}
