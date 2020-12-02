import { deleteResource, fetchResources } from 'api/apis'
import ResourceTable from 'features/resources/ResourceTable'
import { useErrorModalContext } from 'providers/ErrorModalContext'
import { useLoadingContext } from 'providers/LoadingContext'
import React, { useEffect, useState } from 'react'
import { Col, Container, Row } from 'react-bootstrap'
import { setParams } from 'utils/queryParams'

export const UTM =
  '?utm_source=metamug-api-console&utm_medium=link&utm_campaign=product-guide'

function Resources(props) {
  const [error, setError] = useErrorModalContext()

  const [loading, setLoading] = useLoadingContext()

  const [connectionError, setConnectionError] = useState(false)
  const [data, setData] = useState([])

  const openResourceEditor = () => {
    if (data.length >= 20) {
      // openLimitModal()
    } else {
      const url = `/editor?${setParams({
        isNew: true,
        app: 'demo',
      })}`
      props.history.push(url)
    }
  }

  const getResources = async () => {
    try {
      setLoading({
        type: 'open',
        payload: { message: `Getting Resources...` },
      })
      const { data } = await fetchResources()
      setData(data.data)
      setLoading({ type: 'close' })
    } catch (error) {
      setLoading({ type: 'close' })
      setConnectionError(true)
      return setError({
        type: 'open',
        payload: { message: error.message || 'Failed to get resources' },
      })
    }
  }

  useEffect(() => {
    // localStorage.setItem('token', '2e60c561b6bc42c215f7352e7ff16002')
    // localStorage.setItem('defaultItem', 'demo')
    getResources()
  }, [])

  return (
    <Container className="pt-4">
      <Row>
        <Col lg="9">
          <span style={{ fontSize: '32px', marginLeft: '12px' }}>
            Resources{' '}
          </span>
          {/* Tooltip Overlay */}
        </Col>
        <Col lg="3">
          <div className="btn-group float-right">
            {/* <ResourceUploadModal
                  refreshContent={getResources}
                  resourceData={data}
                  openLimitModal={openLimitModal}
                /> */}
            <button
              type="button"
              onClick={openResourceEditor}
              className="btn btn-lg btn-success mr-3"
              title="Create New Resource"
            >
              Create Resource <i className="fa fa-file-code-o fa-fw" />
            </button>
          </div>
        </Col>
      </Row>
      <Container>
        {/* {!loading && ( */}
        <div>
          <br />
          {data.length !== 0 ? (
            <div>
              <ResourceTable data={data} onDelete={getResources} />
            </div>
          ) : (
            <div>
              {connectionError ? (
                <div>
                  <h5 className="text text-danger">Server not reachable!</h5>
                </div>
              ) : (
                <div>
                  <h5>
                    You do not have any resources. Click on New Resource to
                    create a{' '}
                    <a
                      style={{ cursor: 'pointer' }}
                      rel="noreferrer"
                      target="_blank"
                      href={`https://metamug.com/docs/resource-file${UTM}&utm_content=${localStorage.currentApiVersion}`}
                    >
                      resource
                    </a>
                    .
                  </h5>
                </div>
              )}
            </div>
          )}
        </div>
        {/* )} */}
      </Container>
    </Container>
  )
}

export default Resources
