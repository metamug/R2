import { createNewResource, fetchXML, saveResourceXML } from 'api/apis'
import { API_URL_DOMAIN, newResourceDefaultVal } from 'constants/resource'
import { useErrorModalContext } from 'providers/ErrorModalContext'
import { useLoadingContext } from 'providers/LoadingContext'
import React, {
  createContext,
  createRef,
  useContext,
  useEffect,
  useState,
} from 'react'

const ResourceContext = createContext()

const newResourceTitle = 'New Resource'

const initialResource = {
  name: newResourceTitle,
  version: '1.0',
  isNewResource: true,
}

export default function ResourceProvider(props) {
  const cmRef = createRef()

  const [selectedResource, setSelectedResource] = useState(initialResource)

  return (
    <ResourceContext.Provider
      value={{
        state: {
          selectedResource,
        },
        handlers: {
          setSelectedResource,
        },
        refs: { cmRef },
      }}
    >
      {props.children}
    </ResourceContext.Provider>
  )
}

function useResourceContext() {
  const context = useContext(ResourceContext)
  if (context === undefined) {
    throw new Error('Context Unavailable')
  }
  return [context.state, context.handlers, context.refs]
}

export { ResourceContext, useResourceContext }
