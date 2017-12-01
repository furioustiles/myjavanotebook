import React from 'react';
import ContentHeader from './problem_details/content_header.jsx';
import EditContent from './problem_details/edit_content.jsx';
import ReadonlyContent from './problem_details/readonly_content.jsx';
import TestCase from './problem_details/test_case.jsx';

class ProblemDetails extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      problemId: null,
      displayName: null,
      problemSourceURL: null,
      timeCreated: null,
      cells: [],
      focusedCell: null,
      focusedCellIndex: -1,
      contentMode: 'readonly',
    };

    this.getNewProblemData = this.getNewProblemData.bind(this);
    this.setEditMode = this.setEditMode.bind(this);
    this.setReadonlyMode = this.setReadonlyMode.bind(this);
    this.saveProblem = this.saveProblem.bind(this);
    this.updateCell = this.updateCell.bind(this);
    this.changeFocusedCell = this.changeFocusedCell.bind(this);
    this.insertCell = this.insertCell.bind(this);
    this.removeCell = this.removeCell.bind(this);
    this.moveCellUp = this.moveCellUp.bind(this);
    this.moveCellDown = this.moveCellDown.bind(this);
  }

  getNewProblemData(newProblemId) {
    fetch('/api/problem?id='+newProblemId, {
      method: 'get'
    }).then(response => {
      return response.json();
    }).then(data => {
      this.setState({
        problemId: newProblemId,
        displayName: data.displayName,
        problemSourceURL: data.problemSourceURL,
        timeCreated: data.timeCreated,
        cells: data.cells,
        contentMode: 'readonly'
      });
      document.title = data.displayName + ' | MyJavaNotebook';
    });
  }

  getDateTimeString() {
    const date = new Date(this.state.timeCreated);

    const day = date.getDate();
    const month = date.getMonth() - 1;
    const year = date.getFullYear().toString().substr(-2);

    let hours = date.getHours();
    const minutes = (date.getMinutes()).toString().padStart(2, '0');
    const ampm = hours >= 12 ? 'pm' : 'am';
    hours %= 12;
    hours = hours ? hours : 12;

    const dateString = month+'/'+day+'/'+year+' '+hours+':'+minutes+ampm;
    return 'Created ' + dateString;
  }

  setEditMode() {
    this.setState({
      contentMode: 'edit'
    });
  }

  setReadonlyMode() {
    this.setState({
      contentMode: 'readonly'
    });
  }

  saveProblem() {
    const updateForm = JSON.stringify({
      id: this.state.problemId,
      cells: this.state.cells
    });

    fetch('/api/problem/edit', {
      method: 'post',
      headers: new Headers({
        'content-type': 'application/json'
      }),
      body: updateForm
    });
  }

  updateCell(cellPosition, newCellContent) {
    let updatedCells = this.state.cells;
    updatedCells[cellPosition].cellContent = newCellContent;

    this.setState({
      cells: updatedCells
    }, () => {
      this.saveProblem();
    });
  }

  changeFocusedCell(newFocusedCell) {
    const cells = this.state.cells;
    for (var i = 0; i < cells.length; i++) {
      if (cells[i].id === newFocusedCell) {
        this.setState({
          focusedCell: newFocusedCell,
          focusedCellIndex: i
        });
        break;
      }
    }
  }

  insertCell(cellType) {
    if (this.state.focusedCell || this.state.cells.length === 0) {
      const id = this.state.problemId;
      const position = this.state.focusedCellIndex + 1;
      const fetchString = '/api/problem/edit/insert?id='+id+'&cellType='+cellType+'&position='+position;

      fetch(fetchString, {
        method: 'post'
      }).then(response => {
        return response.json();
      }).then(data => {
        this.setState({
          cells: data,
          focusedCell: data[position + 1],
          focusedCellIndex: position + 1
        });
      });
    }
  }

  removeCell() {
    if (this.state.focusedCell) {
      const id = this.state.problemId;
      const position = this.state.focusedCellIndex;
      const fetchString = '/api/problem/edit/delete?id='+id+'&position='+position;

      fetch(fetchString, {
        method: 'delete'
      }).then(response => {
        return response.json();
      }).then(data => {
        this.setState({
          cells: data,
          focusedCell: null,
          focusedCellIndex: -1
        });
      });
    }
  }

  moveCellUp() {
    const focusedCell = this.state.focusedCell;
    const idx = this.state.focusedCellIndex;

    if (focusedCell && idx > 0) {
      let cellList = this.state.cells;

      const tmp = cellList[idx];
      cellList[idx] = cellList[idx-1];
      cellList[idx-1] = tmp;

      this.setState({
        cells: cellList,
        focusedCellIndex: idx - 1
      }, () => {
        this.saveProblem();
      });
    }
  }

  moveCellDown() {
    const focusedCell = this.state.focusedCell;
    const idx = this.state.focusedCellIndex;
    let cellList = this.state.cells;

    if (focusedCell && idx < cellList.length - 1) {
      const tmp = cellList[idx];
      cellList[idx] = cellList[idx+1];
      cellList[idx+1] = tmp;

      this.setState({
        cells: cellList,
        focusedCellIndex: idx + 1
      }, () => {
        this.saveProblem();
      });
    }
  }

  componentWillMount() {
    this.getNewProblemData(this.props.problemId);
  }

  componentWillReceiveProps(newProps) {
    const newProblemId = newProps.problemId;
    if (newProblemId !== this.state.problemId) {
      this.getNewProblemData(newProblemId);
    }
  }

  render() {
    const contentMode = this.state.contentMode;
    const displayName = this.state.displayName;
    const dateTimeString = this.getDateTimeString();
    const problemDetailsHeader = <ContentHeader
        problemId={ this.state.problemId }
        problemSourceURL={ this.state.problemSourceURL }
        displayName={ displayName }
        dateTimeString={ dateTimeString }
        deleteProblem={ this.props.deleteProblem }
    />;
    let problemDetailsBody = null;
    if (contentMode === 'edit') {
      problemDetailsBody = <EditContent
          problemId={ this.state.problemId }
          cells={ this.state.cells }
          focusedCell={ this.state.focusedCell }
          focusedCellIndex={ this.state.focusedCellIndex }
          setReadonlyMode={ this.setReadonlyMode }
          updateCell={ this.updateCell }
          changeFocusedCell={ this.changeFocusedCell }
          insertCell={ this.insertCell }
          removeCell={ this.removeCell }
          moveCellUp={ this.moveCellUp }
          moveCellDown={ this.moveCellDown }
      />;
    } else if (contentMode === 'readonly') {
      problemDetailsBody = <ReadonlyContent
          cells={ this.state.cells }
          setEditMode={ this.setEditMode }
      />;
    }

    return (
      <div id='mjnb-problem-details'>
        { problemDetailsHeader }
        <div id='problem-details-body'>
          { problemDetailsBody }
        </div>
        <TestCase
            problemId={ this.state.problemId }
        />
      </div>
    );
  }
}

export default ProblemDetails;
