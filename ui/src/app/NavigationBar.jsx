import { navigationConfig } from 'app/navigationConfig'
import NavMenuItem from 'app/NavMenuItem'
import React from 'react'
import { Container, Nav, Navbar, NavDropdown } from 'react-bootstrap'
import { Link } from 'react-router-dom'
import MTGLogo from 'assets/images/metamug-console-logo.png'

function NavigationBar() {
  return (
    <Navbar
      sticky="top"
      bg="primary"
      className="text-white"
      style={{ height: '60px', boxSizing: 'border-box' }}
    >
      <Container style={{ paddingLeft: '8px' }}>
        <Navbar.Brand as={Link} style={{ cursor: 'pointer' }} to="/resources">
          <img
            alt="Api console"
            style={{ cursor: 'pointer' }}
            src={MTGLogo}
            height="24px"
          />
        </Navbar.Brand>
        <Nav className="ml-auto" style={{ marginRight: '50px' }}>
          <NavDropdown
            alignRight
            style={{ marginRight: '20px' }}
            title={
              <b className="text-white">
                <i
                  className="fa fa-server fa-fw"
                  style={{
                    fontSize: '18px',
                    verticalAlign: 'middle',
                    marginRight: '1px',
                  }}
                />
                Menu
              </b>
            }
            id="backend-dropdown"
          >
            {Object.keys(navigationConfig.menu).map((key) => (
              <NavMenuItem
                key={navigationConfig.menu[key].path}
                config={navigationConfig.menu[key]}
              />
            ))}
          </NavDropdown>
        </Nav>
      </Container>
    </Navbar>
  )
}

export default NavigationBar
