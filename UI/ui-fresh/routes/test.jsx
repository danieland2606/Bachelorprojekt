export const handler = {
  GET(req, ctx) {
    const url = new URL(req.url);
    const myval = url.searchParams.get("myval");
    console.log(myval);
    return ctx.render(myval);
  },
};

export default function IframeTest({ data }) {
  if (data) {
    return (
      <>
        <h1>{data}</h1>
      </>
    );
  } else {
    return (
      <>
        <form action="" target="my_test" method="get">
          <input type="text" name="myval"></input>
          <input type="submit"></input>
        </form>
        <iframe name="my_test">
        </iframe>
      </>
    );
  }
}
