import React, { useState, useEffect, useContext } from 'react'
import { fetchResources } from 'api/apis'
import { useErrorModalContext } from 'providers/ErrorModalContext'
import { useLoadingContext } from 'providers/LoadingContext'
import CustomList from 'elements/CustomList'
import { ResourceContext } from 'providers/ResourceContext.jsx'

export default function ResourceList() {
  const [setError] = useErrorModalContext()
  const { state, handlers } = useContext(ResourceContext)

  const [loading, setLoading] = useLoadingContext()

  const [ConnectionError, setConnectionError] = useState(false)
  const [data, setData] = useState([])

  const getResources = async () => {
    try {
      setLoading({
        type: 'open',
        payload: { message: `Getting Resources...` },
      })
      const { data } = await fetchResources()
      setData(data.data)
      handlers.setSelectedResource({
        ...state.selectedResource,
        name: data.data[0].name,
        version: data.data[0].version,
        created: data.data[0].created,
      })
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

  const trimAsteriskFromTitle = (name) => {
    return name.replace(/\*/g, '')
  }

  useEffect(() => {
    getResources()
  }, [])

  const config = {
    data: [],
  }

  data.map((value) => {
    config.data.push({
      name: value.name,
      isActive:
        trimAsteriskFromTitle(value.name) ===
        trimAsteriskFromTitle(state.selectedResource.name),
      onClickItem: () =>
        handlers.setSelectedResource({
          ...state.selectedResource,
          name: trimAsteriskFromTitle(value.name),
          version: value.version,
          created: value.created,
        }),
    })
  })

  return (
    <div>
      <h4
        style={{
          marginBottom: '11px',
          marginLeft: '5px',
        }}
      >
        Resources
      </h4>
      <CustomList listConfig={config} />
    </div>
  )
}
