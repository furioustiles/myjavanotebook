import React from 'react'
import Sidebar from './app/sidebar.jsx';
import MainContent from './app/main_content.jsx';

class App extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      problemId: null,
      problemList: [],
      isSidebarLoading: true
    };

    this.selectProblem = this.selectProblem.bind(this);
    this.deleteProblem = this.deleteProblem.bind(this);
    this.getAllProblems = this.getAllProblems.bind(this);
    this.getProblemsByTag = this.getProblemsByTag.bind(this);
  }

  selectProblem(newProblemId) {
    const oldProblemId = this.state.problemId;

    if (newProblemId !== oldProblemId) {
      if (document.getElementById(oldProblemId)) {
        document.getElementById(oldProblemId).classList.remove('problem-highlight');
      }
      this.setState({
        problemId: newProblemId
      }, () => {
        if (document.getElementById(newProblemId)) {
          document.getElementById(newProblemId).classList.add('problem-highlight');
        }
      });
    }

    if (!newProblemId) {
      document.title = 'MyJavaNotebook';
    } else if (newProblemId === 'new') {
      document.title = 'Create new problem | MyJavaNotebook';
    }
  }

  deleteProblem(problemToDelete) {
    fetch('/api/problem/delete?id='+problemToDelete, {
      method: 'delete'
    }).then(() => {
      if (this.state.problemId === problemToDelete) {
        this.selectProblem(null);
      }
      this.getAllProblems();
    });
  }

  getAllProblems() {
    this.setState({
      isSidebarLoading: true
    }, () => {
      fetch('/api/content', {
        method: 'get'
      }).then(response => {
        return response.json();
      }).then(data => {
        this.setState({
          problemList: data.contentBody,
          isSidebarLoading: false
        });
      });
    });
  }

  getProblemsByTag(tag) {
    this.setState({
      isSidebarLoading: true
    }, () => {
      fetch('/api/content/tagged?tag='+tag, {
        method: 'get'
      }).then(response => {
        return response.json();
      }).then(data => {
        this.setState({
          problemList: data.contentBody,
          isSidebarLoading: false
        });
      });
    });
  }

  componentWillMount() {
    this.getAllProblems();
  }

  render() {
    return (
      <div id='mjnb-wrapper'>
        <Sidebar
            problemId={ this.state.problemId }
            problemList={ this.state.problemList }
            isSidebarLoading={ this.state.isSidebarLoading }
            selectProblem={ this.selectProblem }
            getAllProblems={ this.getAllProblems }
            getProblemsByTag={ this.getProblemsByTag }
        />
        <MainContent
            problemId={ this.state.problemId }
            selectProblem={ this.selectProblem }
            deleteProblem={ this.deleteProblem }
            getAllProblems={ this.getAllProblems }
        />
      </div>
    );
  }
}

export default App;
