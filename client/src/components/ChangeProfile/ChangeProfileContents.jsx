import PropType from 'prop-types';
import TutorContents from './TutorContents';
import TuteeContents from './TuteeContents';
import Profile from '../../recoil/profile';
import { useRecoilValue } from 'recoil';

const ChangeProfileContents = ({ user, setUser }) => {
  const { userStatus } = useRecoilValue(Profile);

  return (
    <>
      {userStatus === 'TUTOR' ? (
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
