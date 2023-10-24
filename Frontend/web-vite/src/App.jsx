import { BrowserRouter } from "react-router-dom";
import { UserProvider } from "./Contexts/UserContext.jsx";
import RouteComponent from "./Routes/index.jsx";
import { ThemeProvider } from "./Contexts/ThemeContext.jsx";

function App() {
  return (
    <ThemeProvider>
      <BrowserRouter>
        <UserProvider>
          <RouteComponent />
        </UserProvider>
      </BrowserRouter>
    </ThemeProvider>
  );
}

export default App;
