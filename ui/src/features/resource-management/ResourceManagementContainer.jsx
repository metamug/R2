import React from 'react'
import { Row } from 'react-bootstrap'
import ResourceList from './ResourceList'
import ResourceManagementEditor from './ResourceManagementEditor'
import Reference from './Reference'

export default function ResourceManagement() {
  return (
    <div>
      <p style={{ fontSize: '28px', marginTop: '25px' }}>Resource Manager</p>
      <Row className="mr-0">
        <div className="col-md-3 mt-3 ">
          <ResourceList />
        </div>
        <div className="col-md-6 mt-2 pl-0">
          <ResourceManagementEditor />
        </div>
        <div className="col-md-3 mt-3 pl-0">
          <Reference />
        </div>
      </Row>
    </div>
  )
}
