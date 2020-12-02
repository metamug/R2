import React, { useEffect, useState } from 'react'
import { Button, Container, Form } from 'react-bootstrap'
import { useErrorModalContext } from 'providers/ErrorModalContext.jsx'
import { useLoadingContext } from 'providers/LoadingContext.jsx'
import { encodePasswordBase64, userLoggedIn } from 'utils/auth.js'
import { doLogin } from 'api/apis'

const defaultInputState = {
  username: '',
  password: '',
}

function LoginView(props) {
  const [error, setError] = useErrorModalContext()
  const [loading, setLoading] = useLoadingContext()

  const [inputState, setInputState] = useState(defaultInputState)

  const { username, password } = inputState

  const handleSubmit = async (e) => {
    e.preventDefault()
    try {
      setLoading({
        type: 'open',
        payload: { message: `Signing In...` },
      })
      const { data } = await doLogin(username, encodePasswordBase64(password))
      await localStorage.setItem('token', data.token)
      localStorage.setItem('defaultItem', 'demo')
      props.history.push('/resources')
      setLoading({ type: 'close' })
    } catch (error) {
      setLoading({ type: 'close' })
      setError({
        type: 'open',
        payload: { message: error.message || 'Failed to sign in' },
      })
    }
  }

  const handleChange = (e) => {
    e.preventDefault()
    setInputState({
      ...inputState,
      [e.target.name]: e.target.value,
    })
  }

  useEffect(async () => {
    const isLoggedIn = await userLoggedIn()
    if (isLoggedIn) {
      props.history.push('/resources')
    } else {
      localStorage.clear()
    }
  }, [])

  return (
    <Container className="pt-4">
      {/*<h4>Login</h4>*/}
      {/*<br />*/}
      <Form
        className="col-md-4 col-md-offset-4 mx-auto mt-auto"
        onSubmit={handleSubmit}
      >
        <Form.Group>
          <Form.Label>Username</Form.Label>
          <Form.Control
            type="text"
            name="username"
            value={username}
            onChange={handleChange}
            placeholder="Username"
            required
          />
        </Form.Group>
        <Form.Group>
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="password"
            name="password"
            value={password}
            placeholder="Password"
            onChange={handleChange}
            required
          />
          {/* <a href="#/forgot-password" style={{cursor:"pointer",fontWeight:"bold"}}>Forgot password?</a>
           */}
        </Form.Group>
        <Button type="submit" variant="success">
          Login
        </Button>
        <br />
        {error && !loading && (
          <p className="text text-danger">Invalid email/password</p>
        )}
      </Form>
    </Container>
  )
}

export default LoginView
