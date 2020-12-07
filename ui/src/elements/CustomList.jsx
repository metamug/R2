import React from 'react'
import { ListGroup } from 'react-bootstrap'
import styled from 'styled-components'

const StyledListItem = styled(ListGroup.Item)`
  margin: 3px 6px 0 5px;
  cursor: pointer;

  & .list-group-item,
  .active {
    margin: 3px 6px 0 5px;
  }
`
const CustomScrollBarDiv = styled.div`
  --scrollbarBG: #e4ecef;
  --thumbBG: #90a4ae;

  scrollbar-width: thin;
  scrollbar-color: var(--thumbBG) var(--scrollbarBG);

  &::-webkit-scrollbar {
    width: 12px;
  }
  &::-webkit-scrollbar-track {
    background: var(--scrollbarBG);
  }
  &::-webkit-scrollbar-thumb {
    background-color: var(--thumbBG);
    border-radius: 8px;
    border: 3px solid var(--scrollbarBG);
  }
`

const ScrollableDivs = styled(CustomScrollBarDiv)`
  height: 85%;
  overflow-y: auto;

  & thead th {
    position: sticky;
    top: 0;
  }
`

export default function CustomList({ listConfig }) {
  const { data } = listConfig

  const body = data.map((obj, i) => (
    <StyledListItem
      active={obj.isActive}
      key={obj.name}
      onClick={obj.isActive ? null : obj.onClickItem}
    >
      {obj.name}
    </StyledListItem>
  ))

  return (
    <div>
      <ScrollableDivs>
        <ListGroup>{body}</ListGroup>
      </ScrollableDivs>
    </div>
  )
}
