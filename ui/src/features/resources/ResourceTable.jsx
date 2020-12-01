/* eslint-disable react/display-name */
import { createNewResource, deleteResource, fetchXML } from 'api/apis'
import { API_URL_DOMAIN } from 'constants/resource'
import DeleteAlertModal from 'features/resources/DeleteAlertModal'
import { useErrorModalContext } from 'providers/ErrorModalContext'
import { useLoadingContext } from 'providers/LoadingContext'
import React, { useEffect, useState } from 'react'
import { Button, Form, Modal } from 'react-bootstrap'
import BootstrapTable from 'react-bootstrap-table-next'
import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit'
import { Link } from 'react-router-dom'
import { getPrintableDate } from 'utils/date'
import { setParams } from 'utils/queryParams'

export const customStyles = {
  content: {
    position: 'fixed',
    border: '2px solid #ccc',
    top: '40%',
    left: '50%',
    right: '70%',
    bottom: 'auto',
    marginRight: '-50%',
    transform: 'translate(-50%, -50%)',
    background: '#fff',
  },
}
const searchStyle = {
  position: 'relative',
  display: 'flex',
  width: '370px',
  marginLeft: '710px',
}

const initialData = []

function ResourceTable(props) {
  const [error, setError] = useErrorModalContext()

  const [loading, setLoading] = useLoadingContext()

  const [data, setData] = useState(initialData)
  const [deleteModalOpen, setDeleteModalOpen] = useState(false)
  const [renaming, setRenaming] = useState(false)
  const [selectedForRename, setSelectedForRename] = useState({
    name: '',
    version: '',
  })
  const [newName, setNewName] = useState(selectedForRename.name)

  const { SearchBar } = Search
  const columns = [
    {
      dataField: 'secure',
      text: 'secure',
      hidden: true,
      searchable: false,
      headerTitle: true,
    },
    {
      dataField: 'name',
      text: 'Name',
      sort: true,
      headerAlign: 'center',
      headerTitle: true,
      headerStyle: {
        fontWeight: 'bold',
      },
      formatter: (cell, row) => {
        const url = `/editor?${setParams({
          name: row.name,
          version: row.version,
          app: 'demo',
          isNew: false,
        })}`
        return (
          <div className="text-center">
            <Link
              style={{ textDecoration: 'none' }}
              // href={this.makeHrefTarget(row.name, row.version)}
              to={url}
            >
              <span>{row.name}</span>
            </Link>
          </div>
        )
      },
    },
    {
      dataField: 'version',
      text: 'Version',
      sort: true,
      align: 'center',
      headerAlign: 'center',
      headerTitle: true,
      headerStyle: {
        fontWeight: 'bold',
      },
    },
    {
      dataField: 'endpoint',
      text: 'Endpoint',
      sort: true,
      headerAlign: 'center',
      headerStyle: {
        fontWeight: 'bold',
      },
      headerTitle: true,
      formatter: (_cell, row) => {
        return (
          <div className="text-center">
            <a
              className="text-success"
              style={{ textDecoration: 'none', cursor: 'pointer' }}
              onClick={() => openRes(this, row.version, row.name)}
            >
              {row.endpoint}
            </a>
          </div>
        )
      },
    },
    {
      dataField: 'lastmodified',
      text: 'Last Modified',
      sort: true,
      headerStyle: {
        fontWeight: 'bold',
      },
      headerAlign: 'center',
      headerTitle: true,
      align: 'center',
    },

    {
      dataField: 'options',
      text: 'Options',
      headerTitle: true,
      headerAlign: 'center',
      headerStyle: {
        fontWeight: 'bold',
      },
      formatter: (cell, row) => {
        return (
          <div className="text-center">
            <span
              title="Download Resource"
              style={{ cursor: 'pointer' }}
              onClick={() => getXMLAndDownloadResource(row.name, row.version)}
              className="fa fa-lg fa-download fa-fw"
            />
            <span
              title="Rename Resource"
              style={{ cursor: 'pointer' }}
              onClick={() => openRenameModal(row.name, row.version)}
              className="fa fa-lg fa-edit fa-fw"
            />
            <DeleteAlertModal
              onConfirm={() => deleteFileHandler(row.name, row.version)}
              message="Are you sure you want to remove this resource?"
              title="Delete Resource"
              objectName={row.name}
            />
          </div>
        )
      },
    },
  ]

  const openRenameModal = (name, version) => {
    setRenaming(true)
    setSelectedForRename({ name, version })
  }

  const openRes = (_, version, name) => {
    const url = `${API_URL_DOMAIN}/${localStorage.defaultItem}/${version}/${name}`
    window.open(url, '_blank')
  }

  const deleteFileHandler = async (name, version) => {
    // type "all" means both XML and JSP should be deleted
    await deleteResourceFile(name, version, 'all')
  }

  const deleteResourceFile = async (name, version, type) => {
    setLoading({
      type: 'open',
      payload: { message: `Deleting Resources...` },
    })
    await deleteResource(name, version, type)
    if (type !== 'jsp') {
      setRenaming(false)
      setDeleteModalOpen(false)
      await props.onDelete()
    }
  }

  const getXMLForSelectedResource = async (name, version) => {
    try {
      const { data } = await fetchXML(name, version)
      return data
    } catch (err) {
      return setError({
        type: 'open',
        payload: { message: error.message || 'Failed to get XML' },
      })
    }
  }

  const createNewResourceWithSameXML = async (xml, newResourceName) => {
    try {
      if (/^[a-z]+$/.test(newResourceName)) {
        await createNewResource(newResourceName, xml)
      } else {
        throw new Error('Invalid resource name: ' + newResourceName)
      }
    } catch (error) {
      throw new Error(error)
    }
  }

  const renameHandler = async (e) => {
    e.preventDefault()
    setRenaming(false)
    setLoading({
      type: 'open',
      payload: { message: 'Renaming resource: ' + selectedForRename.name },
    })
    try {
      const { name, version } = selectedForRename
      const xml = await getXMLForSelectedResource(name, version)
      await createNewResourceWithSameXML(xml, newName, name, version)
      await deleteFileHandler(name, version)
    } catch (error) {
      setLoading({ type: 'close' })
      return setError({
        type: 'open',
        payload: { message: error.message || 'Failed to rename resource' },
      })
    }
  }

  const downloadAsFile = (filename, text) => {
    const element = document.createElement('a')
    element.setAttribute(
      'href',
      `data:text/plain;charset=utf-8,${encodeURIComponent(text)}`
    )
    element.setAttribute('download', filename)
    element.style.display = 'none'
    document.body.appendChild(element)
    element.click()
    document.body.removeChild(element)
  }

  const getXMLAndDownloadResource = async (name, version) => {
    await fetchXML(name, version)
      .then((res) => {
        downloadAsFile(`${name}.xml`, res.data)
      })
      .catch((err) =>
        alert('Unable to connect to server. Please check your connection!')
      )
  }

  useEffect(() => {
    const data = []
    for (let i = 0; i < props.data.length; i++) {
      const obj = {}
      const row = props.data[i]
      obj.name = row.name
      obj.version = row.version
      //   obj.secure = row.secure
      if (row.tag) {
        obj.tags = row.tag
      } else {
        obj.tags = ''
      }
      obj.endpoint = `/${row.version}/${row.name}`
      obj.lastmodified = getPrintableDate(row.created)
      obj.options = 'NA'
      data.push(obj)
    }
    setData(data)
  }, [props.data])

  return (
    <div>
      <br />
      <ToolkitProvider
        bootstrap4
        keyField="endpoint"
        data={data}
        columns={columns}
        search
      >
        {(props) => (
          <div>
            <SearchBar
              {...props.searchProps}
              className="custome-search-field"
              style={searchStyle}
              placeholder="Search..."
            />
            <br />
            <BootstrapTable
              striped
              defaultSortDirection="asc"
              responsive
              hover
              condensed
              className="table table-striped table-bordered table-hover"
              {...props.baseProps}
            />
          </div>
        )}
      </ToolkitProvider>

      {/* Rename Modal */}
      <Modal
        contentLabel="rename-resource-modal"
        show={renaming}
        onHide={() => setRenaming(false)}
      >
        <Modal.Header closeButton>
          <h6
            style={{
              fontWeight: 'bold',
              fontSize: '16px',
              paddingTop: '10px',
            }}
          >
            Rename Resource
          </h6>
        </Modal.Header>
        <Modal.Body>
          <Form.Control
            // onKeyPress={this.handleSaveKeyPress}
            type="text"
            name="newname"
            defaultValue={selectedForRename.name}
            // value={newName}
            onChange={(e) => setNewName(e.target.value)}
          />
        </Modal.Body>
        <Modal.Footer>
          <Button variant="success" onClick={renameHandler}>
            OK
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  )
}

export default ResourceTable
