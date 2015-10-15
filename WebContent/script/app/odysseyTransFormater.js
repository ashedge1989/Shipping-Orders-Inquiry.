

// NOTE:	Format required for jTree - convert incoming JSON in this format for jTree to interprete it.

/*	var data = [ {
	label : 'node1',
	children : [ {
		label : 'child1'
	}, {
		label : 'child2'
	} ]
}, {
	label : 'node2',
	children : [ {
		label : 'child3'
	} ]
} ];	*/

//console.dir(retData);
// Need to convert the actual JSON to match the above format. 
function formatJSON(retData) {
	var data = [];

	var nodeOrderId = {};
	nodeOrderId.label = "orderId : " + retData.orderId;
	data.push(nodeOrderId);

	var nodeFrom = {};
	nodeFrom.label = "From";
	nodeFrom.children = [];
	var childZip = {};
	childZip.label = "zip : " + retData.from.zip;
	var childState = {};
	childState.label = "state : " + retData.from.state;
	var childCity = {};
	childCity.label = "city : " + retData.from.city;
	nodeFrom.children.push(childZip);
	nodeFrom.children.push(childState);
	nodeFrom.children.push(childCity);

	var nodeTo = {};
	nodeTo.label = "To";
	nodeTo.children = [];
	var childZip = {};
	childZip.label = "zip : " + retData.to.zip;
	var childState = {};
	childState.label = "state : " + retData.to.state;
	var childCity = {};
	childCity.label = "city : " + retData.to.city;
	nodeTo.children.push(childZip);
	nodeTo.children.push(childState);
	nodeTo.children.push(childCity);

	var nodeInst = {};
	nodeInst.label = "instructions : " + retData.instructions;

	var nodeLines = {};
	nodeLines.label = "Lines";
	nodeLines.children = [];
	// add a single line to children array
	var allLines = retData.lines;
	for ( var lineNum in allLines) {
		// single line
		var singleLine = {};
		singleLine.label = "line";
		singleLine.children = []; // singleLine has childrens
		var childWeight = {};
		childWeight.label = "weight : " + allLines[lineNum].weight;
		var childVolume = {};
		childVolume.label = "volume : " + allLines[lineNum].volume;
		var childHazard = {};
		childHazard.label = "hazard : " + allLines[lineNum].hazard;
		var childProduct = {};
		childProduct.label = "product : " + allLines[lineNum].product;
		singleLine.children.push(childWeight);
		singleLine.children.push(childVolume);
		singleLine.children.push(childHazard);
		singleLine.children.push(childProduct);
		// add singleLine to the Lines
		nodeLines.children.push(singleLine);
	}

	data.push(nodeFrom);
	data.push(nodeTo);
	data.push(nodeLines);
	data.push(nodeInst);

	// console.log("Built obj:");
	// console.dir(data);

	return data;
}


