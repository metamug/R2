// delete-alert.jsx
import React, { useState } from 'react'
import { Modal } from 'react-bootstrap'

function DeleteAlertModal({
  onConfirm,
  title,
  btn,
  message,
  objectName,
  closeModal,
}) {
  const [modalOpen, setModalOpen] = useState(false)

  return (
    <React.Fragment>
      <span
        title={title}
        onClick={() => setModalOpen(true)}
        style={{ cursor: 'pointer' }}
        className={
          btn
            ? 'btn btn-default fa fa-trash'
            : title === 'Delete DataSource'
            ? 'fa fa-trash fa-lg text-success'
            : 'fa fa-lg fa-trash-o fa-fw'
        }
      />

      <Modal contentLabel="delete-resource-modal" show={modalOpen}>
        <div>
          <div className="modal-header">
            <h5>{message}</h5>
          </div>

          <div className="modal-body">
            <div>
              <b>{objectName}</b>
            </div>
          </div>

          <div className="modal-footer">
            <button
              type="button"
              className="btn btn-danger"
              onClick={() => {
                setModalOpen(false)
                onConfirm()
              }}
            >
              Yes
            </button>
            <button
              type="button"
              className="btn btn-default"
              onClick={() => setModalOpen(false)}
            >
              No
            </button>
          </div>
        </div>
      </Modal>
    </React.Fragment>
  )
}

export default DeleteAlertModal
