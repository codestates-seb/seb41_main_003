import PropType from 'prop-types';
import TutorContents from './TutorContents';
import TuteeContents from './TuteeContents';

const ChangeProfileContents = ({ user, setUser }) => {
  const { profile_status } = user;

  return (
    <>
      {profile_status === 'TUTOR' ? (
        <TutorContents user={user} setUser={setUser} />
      ) : (
        <TuteeContents user={user} setUser={setUser} />
      )}
    </>
  );
};

ChangeProfileContents.propTypes = {
  user: PropType.object,
  setUser: PropType.func,
};

export default ChangeProfileContents;
