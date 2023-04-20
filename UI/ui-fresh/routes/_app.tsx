import { asset, Head } from "$fresh/runtime.ts";
import { AppProps } from "$fresh/src/server/types.ts";

export default function App({ Component }: AppProps) {
  return (
    <html>
      <Head>
        <meta
          name="description"
          content="MeowMed+ customer management application"
        />
        <meta name="author" content="Julian Opitz" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href={asset("meowmed.css")} />
      </Head>
      <body>
        <Component />
      </body>
    </html>
  );
}
