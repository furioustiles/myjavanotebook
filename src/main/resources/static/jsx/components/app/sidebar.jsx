import React from 'react';
import SidebarHeader from './sidebar/sidebar_header.jsx';
import ProblemMenu from './sidebar/problem_menu.jsx';

class Sidebar extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      problemId: this.props.problemId,
      problemList: this.props.problemList,
      isSidebarLoading: this.props.isSidebarLoading
    };

    this.selectProblem = this.props.selectProblem.bind(this);
    this.getProblemsByTag = this.props.getProblemsByTag.bind(this);
  }

  componentWillReceiveProps(newProps) {
    this.setState({
      problemId: newProps.problemId,
      problemList: newProps.problemList,
      isSidebarLoading: newProps.isSidebarLoading
    });
  }

  render() {
    return (
      <div id='mjnb-sidebar'>
        <SidebarHeader
            selectProblem={ this.selectProblem }
            getAllProblems={ this.props.getAllProblems }
            getProblemsByTag={ this.getProblemsByTag }
        />
        <ProblemMenu
            problemList={ this.state.problemList }
            selectProblem={ this.selectProblem }
        />
      </div>
    );
  }
}

export default Sidebar;
