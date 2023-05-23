/** @type {import('tailwindcss').Config} */
import daisyui from "daisyui";
export default {
  content: [
    "./components/**/*.{jsx,tsx}",
    "./routes/**/*.{jsx,tsx}",
    "./islands/**/*.{jsx,tsx}",
  ],
  theme: {
    extend: {
      backgroundSize: {
        "auto": "auto",
        "cover": "cover",
        "contain": "contain",
        "15": "15rem",
        "20": "20rem",
        "22": "22rem",
      },
    },
  },
  plugins: [daisyui],
};
