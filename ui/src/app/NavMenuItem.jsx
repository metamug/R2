import React, { Fragment } from 'react'
import { NavDropdown } from 'react-bootstrap'
import { Link } from 'react-router-dom'

function NavMenuItem({ config }) {
  const { path, src, alt, title, width } = config
  return (
    <Fragment>
      <NavDropdown.Item as={Link} to={path} style={{ paddingTop: '7px' }}>
        <img width={width} src={src} alt={alt} />
        <span style={{ marginLeft: '7px' }}> {title} </span>
      </NavDropdown.Item>
      <NavDropdown.Divider />
    </Fragment>
  )
}

export default NavMenuItem
