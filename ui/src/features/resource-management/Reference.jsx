import React, { useContext } from 'react'
import { ListGroup } from 'react-bootstrap'
import styled from 'styled-components'
import { ResourceContext } from 'providers/ResourceContext.jsx'

const StyledListItem = styled(ListGroup.Item)`
  margin: 3px 6px 0 5px;

  & .list-group-item,
  .active {
    margin: 3px 6px 0 5px;
  }
`

export default function () {
  const { state, handlers } = useContext(ResourceContext)

  const details = (
    <div>
      <StyledListItem>
        {' '}
        <span style={{ marginRight: '15px', fontSize: '16px' }}>Name:</span>
        {state.selectedResource.name}{' '}
      </StyledListItem>
      <StyledListItem>
        {' '}
        <span style={{ marginRight: '15px', fontSize: '16px' }}>Version: </span>
        {state.selectedResource.version}{' '}
      </StyledListItem>
      <StyledListItem>
        {' '}
        <span style={{ marginRight: '15px', fontSize: '16px' }}>
          EndPoints:{' '}
        </span>{' '}
      </StyledListItem>
      <StyledListItem>
        {' '}
        <span style={{ marginRight: '15px', fontSize: '16px' }}>
          Created On:{' '}
        </span>
        {state.selectedResource.created}{' '}
      </StyledListItem>
    </div>
  )

  return (
    <div>
      <h4 style={{ marginLeft: '4px' }}>Reference</h4>
      <div style={{ marginTop: '13px', width: '345px' }}>{details}</div>
    </div>
  )
}
