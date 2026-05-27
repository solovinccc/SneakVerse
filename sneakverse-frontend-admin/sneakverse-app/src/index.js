import React from 'react';
import ReactDOM from 'react-dom';
import AppComponent from './AppComponent';


export default function main({portletNamespace, contextPath, portletElementId, configuration}) {
    ReactDOM.render(
        <AppComponent 
            portletNamespace={portletNamespace} 
            contextPath={contextPath}
            portletElementId={portletElementId}
            configuration={configuration}
        />,
        document.getElementById(portletElementId)
    );
}