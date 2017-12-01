import React from 'react';
import NewProblemForm from './main_content/new_problem_form.jsx';
import ProblemDetails from './main_content/problem_details.jsx';

class MainContent extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      problemId: null,
    };
  }

  componentWillReceiveProps(newProps) {
    this.setState({
      problemId: newProps.problemId
    });
  }

  render() {
    const problemId = this.state.problemId;
    const placeholderImage = null;

    if (!problemId) {
      return (
        <div id='mjnb-main-content' className='main-content-placeholder'>
          <div className='placeholder-inner'>
            { placeholderImage }
            <h1>Select a problem on the left</h1>
          </div>
        </div>
      );
    }

    let mainContent;
    if (problemId === 'new') {
      mainContent = (
        <NewProblemForm
          selectProblem={ this.props.selectProblem }
          getAllProblems={ this.props.getAllProblems }
        />
      );
    } else {
      mainContent = (
        <ProblemDetails
          problemId={ this.state.problemId }
          deleteProblem={ this.props.deleteProblem }
        />
      );
    }

    return (
      <div id='mjnb-main-content'>
        { mainContent }
      </div>
    );
  }
}

export default MainContent;
