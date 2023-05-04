import { asset, Head } from "$fresh/runtime.ts";
import { AppProps } from "$fresh/src/server/types.ts";

export default function App({ Component }: AppProps) {
  return (
    <>
      <Head>
        <title>Meowmed+</title>
        <meta
          name="description"
          content="MeowMed+ customer management application"
        >
        </meta>
        <meta name="author" content="Julian Opitz"></meta>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        </meta>
        <link rel="stylesheet" href={asset("/meowmed.css")}></link>
      </Head>
      <Component></Component>
    </>
  );
}
