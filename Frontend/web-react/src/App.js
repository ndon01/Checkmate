import { BrowserRouter } from "react-router-dom";
import { UserProvider } from "./Contexts/UserContext";
import RouteComponent from "./Routes";
import { ThemeProvider } from "./Contexts/ThemeContext";

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
