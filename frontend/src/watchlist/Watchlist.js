import { useState, useEffect } from 'react';
import '../App.css';
import Duration from '../show/Duration';
import { Card, CardBody, CardSubtitle, CardTitle, Col, Container, Row } from 'reactstrap';
import Poster from '../show/Poster';
import Title from '../show/Title';

export default function Watchlist({ type }) {
  const [shows, setShows] = useState([]);

  const searchParams = new URLSearchParams({
    sort: "position"
  }).toString();
  useEffect(() => {
    fetch("/shows?" + searchParams)
      .then(result => result.json())
      .then(result => setShows(result._embedded.shows));
  }, []);

  return (
    <div className='mt-5 pt-5'>
      {shows.map(show =>
        <Container key={show.id} className='mb-2' style={{'max-width': '500px'}}>
          <Card color="dark" inverse>
            <Row>
              <Col xs='3'>
                <Poster image={show.poster}/>
              </Col>
              <Col>
                <CardBody>
                  <CardTitle tag="h4">
                    <Title show={show}/>
                  </CardTitle>
                  <CardSubtitle>
                    <Duration duration={show.duration} />
                  </CardSubtitle>
                </CardBody>
              </Col>
            </Row>
          </Card>
        </Container>
      )}
    </div>
  );
}
