import React from 'react';
import ProblemRow from './problem_menu/problem_row.jsx';

class ProblemMenu extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      problemList: this.props.problemList,
      isSidebarLoading: this.props.isSidebarLoading
    };

  }

  renderProblemRow(problem) {
    return <ProblemRow
        key={ problem.fileName }
        problemData={ problem }
        selectProblem={ this.props.selectProblem }
    />;
  }

  componentWillReceiveProps(newProps) {
    this.setState({
      problemList: newProps.problemList,
      isSidebarLoading: newProps.isSidebarLoading
    });
  }

  render() {
    let problemMenuInner = null;

    if (this.state.isSidebarLoading) {
      problemMenuInner = (
        <div className='problem-menu problem-menu-placeholder'>
          <h3>Loading...</h3>
        </div>
      );
    } else if (this.state.problemList.length === 0) {
      problemMenuInner = (
        <div className='problem-menu problem-menu-placeholder'>
          <h3>No problems to select</h3>
        </div>
      );
    } else {
      problemMenuInner = (
        <div className='problem-menu'>
          <ul id='problem-list'>
            {
              this.state.problemList.map(problem => {
                return this.renderProblemRow(problem);
              })
            }
          </ul>
        </div>
      );
    }

    return (
      <div id='mjnb-problem-menu-wrapper'>
        { problemMenuInner }
      </div>
    );
  }
}

export default ProblemMenu;
