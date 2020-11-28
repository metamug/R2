import React, { Component } from 'react'
import DocIcon from "assets/images/api-docs/api-docs-bg.svg";
import { RedocStandalone } from 'redoc'
import { API_URL } from 'constants/resource.js'

class APIDocs extends Component {
  constructor(props) {
    super(props)

    this.state = {
      spec: null,
    }

    this.getSpec = () => {
      const app = this.props.match.params.app
      // const app = this.getParameterByName('app')
      // console.log(API_URL + '/m/v1.0/openapi/'+app);
      fetch(`${API_URL}/m/v1.0/openapi/${app}`, {
        method: 'GET',
        headers: {
          Authorization: localStorage.token,
        },
      })
        .then((res) => res.json())
        .then((response) => {
          const spec = JSON.parse(response.spec)
          // console.log(spec)
          this.setState({ spec })
        })
        .catch((err) => {
          // console.log(err);
        })
    }
  }

  componentDidMount() {
    this.getSpec()
  }

  render() {
    return (
      <div className="text-center">
        {this.state.spec == null ? <img style={{height:'570px'}} src={DocIcon} alt="API Docs"/>
          : <RedocStandalone spec={this.state.spec} /> }
      </div>
    )
  }
}

export default APIDocs
